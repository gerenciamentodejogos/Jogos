package com.example.gerenciamentodejogos.resultados


import android.arch.lifecycle.ViewModelProviders
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getSystemService
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados.PAGINA_CARREGADA
import com.example.gerenciamentodejogos.dados_web.*
import kotlinx.android.synthetic.main.fragmento_detalhes_resultado.*
import kotlinx.android.synthetic.main.fragmento_princ_prox_jogo.*
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class FragmentoDetalhesResultado : Fragment() {
    private lateinit var resultadosViewModel: ResultadosViewModel

    val URL_CAIXA = "http://loterias.caixa.gov.br/wps/portal/loterias/landing/megasena/!ut/p/a1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOLNDH0MPAzcDbwMPI0sDBxNXAOMwrzCjA0sjIEKIoEKnN0dPUzMfQwMDEwsjAw8XZw8XMwtfQ0MPM2I02-AAzgaENIfrh-FqsQ9wNnUwNHfxcnSwBgIDUyhCvA5EawAjxsKckMjDDI9FQE-F4ca/dl5/d5/L2dBISEvZ0FBIS9nQSEh/pw/Z7_HGK818G0KO6H80AU71KG7J0072/res/id=buscaResultado/c=cacheLevelPage/=/?timestampAjax=1565677735698&concurso="

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.let {
            resultadosViewModel = ViewModelProviders.of(it).get(ResultadosViewModel::class.java)
        }

        return inflater.inflate(R.layout.fragmento_detalhes_resultado, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
    }

    fun subscribe() {
        var concurso = 0
        arguments?.let {
             concurso = it.getInt(NUMERO_CONCURSO)
        }
        textView_ganhadores.text = concurso.toString()
        val urlconcurso = URL_CAIXA.plus(concurso)
        DownloadTask().execute(urlconcurso)
    }

    companion object {
        val NUMERO_CONCURSO = "numero_concurso"

        fun newInstance(numConcurso: Int): FragmentoDetalhesResultado {
            val fragmentoDetalhesResultado = FragmentoDetalhesResultado()
            fragmentoDetalhesResultado.arguments = Bundle().apply {
                putInt(NUMERO_CONCURSO, numConcurso)
            }
            return fragmentoDetalhesResultado
        }
    }

    inner class DownloadTask: AsyncTask<String, Unit, String>() {
        override fun onPreExecute() {

        }

        override fun doInBackground(vararg urls: String): String? {
            var result: String? = null
            if (!isCancelled && urls.isNotEmpty()) {
                val urlString = urls[0]
                result = try {
                    val url = URL(urlString)
                    downloadUrl(url) ?: throw IOException("No response received.")
                } catch (e: Exception) {
                    e.message
                }
            }
            return result
        }

        /**
         * Updates the DownloadCallback with the result.
         */
        override fun onPostExecute(result: String?) {
            textView_data_sorteio.text = result
        }

        /**
         * Given a URL, sets up a connection and gets the HTTP response body from the server.
         * If the network request is successful, it returns the response body in String form. Otherwise,
         * it will throw an IOException.
         */
        @Throws(IOException::class)
        private fun downloadUrl(url: URL): String? {
            var connection: HttpURLConnection? = null
            return try {
                connection = (url.openConnection() as? HttpURLConnection)
                connection?.run {
                    // Timeout for reading InputStream arbitrarily set to 3000ms.
                    readTimeout = 30000
                    // Timeout for connection.connect() arbitrarily set to 3000ms.
                    connectTimeout = 30000
                    // For this use case, set HTTP method to GET.
                    requestMethod = "GET"
                    // Already true by default but setting just in case; needs to be true since this request
                    // is carrying an input (response) body.
                    doInput = true
                    // Open communications link (network traffic occurs here).
                    connect()

                    if (responseCode != HttpURLConnection.HTTP_OK) {
                        throw IOException("HTTP error code: $responseCode")
                    }
                    // Retrieve the response body as an InputStream.
                    inputStream?.let { stream ->
                        // Converts Stream to String with max length of 500.
                        readStream(stream, 500)
                    }
                }
            } finally {
                // Close Stream and disconnect HTTPS connection.
                connection?.inputStream?.close()
                connection?.disconnect()
            }
        }

        /**
         * Converts the contents of an InputStream to a String.
         */
        @Throws(IOException::class, UnsupportedEncodingException::class)
        fun readStream(stream: InputStream, maxReadSize: Int): String? {
            val reader: Reader? = InputStreamReader(stream, "UTF-8")
            val rawBuffer = CharArray(maxReadSize)
            val buffer = StringBuffer()
            var readSize: Int = reader?.read(rawBuffer) ?: -1
            var maxReadBytes = maxReadSize
            while (readSize != -1 && maxReadBytes > 0) {
                if (readSize > maxReadBytes) {
                    readSize = maxReadBytes
                }
                buffer.append(rawBuffer, 0, readSize)
                maxReadBytes -= readSize
                readSize = reader?.read(rawBuffer) ?: -1
            }
            return buffer.toString()
        }

    }
}
