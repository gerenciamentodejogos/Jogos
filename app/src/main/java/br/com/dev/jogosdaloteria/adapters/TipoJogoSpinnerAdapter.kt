package br.com.dev.jogosdaloteria.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import br.com.dev.jogosdaloteria.R
import br.com.dev.jogosdaloteria.modelos.DadosDoJogo

class TipoJogoSpinnerAdapter(context: Context, itens: List<DadosDoJogo>): ArrayAdapter<DadosDoJogo>(context, R.layout.item_spinner_tipo_jogo, itens) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return criarView(position, convertView, parent, true)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return criarView(position, convertView, parent)
    }

    private fun criarView(position: Int, convertView: View?, parent: ViewGroup, centralizar: Boolean = false): View {
        val view = if (convertView == null) {
            LayoutInflater.from(context).inflate(R.layout.item_spinner_tipo_jogo, parent, false)
        } else {
            convertView
        }

        val textView = view.findViewById<TextView>(R.id.text_view_texto_spinner_tipo_jogo)
        val imageView = view.findViewById<ImageView>(R.id.image_view_icone_spinner_tipo_jogo)

        val tipoJogo = getItem(position)

        if (tipoJogo != null) {
            textView.text = tipoJogo.nome
            textView.setTextColor(tipoJogo.corPrimaria)
            if (centralizar) {
                textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
            imageView.setImageResource(tipoJogo.icone())
        }

        return view
    }
}