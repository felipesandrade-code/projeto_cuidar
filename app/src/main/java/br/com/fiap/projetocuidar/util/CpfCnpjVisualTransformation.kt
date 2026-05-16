package br.com.fiap.projetocuidar.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.TransformedText

class CpfCnpjVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 14) text.text.take(14) else text.text
        var out = ""
        
        if (trimmed.length <= 11) {
            // CPF Mask: 000.000.000-00
            for (i in trimmed.indices) {
                out += trimmed[i]
                if (i == 2 || i == 5) out += "."
                if (i == 8) out += "-"
            }
        } else {
            // CNPJ Mask: 00.000.000/0000-00
            for (i in trimmed.indices) {
                out += trimmed[i]
                if (i == 1 || i == 4) out += "."
                if (i == 7) out += "/"
                if (i == 11) out += "-"
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (trimmed.length <= 11) {
                    if (offset <= 2) return offset
                    if (offset <= 5) return offset + 1
                    if (offset <= 8) return offset + 2
                    if (offset <= 11) return offset + 3
                    return out.length
                } else {
                    if (offset <= 1) return offset
                    if (offset <= 4) return offset + 1
                    if (offset <= 7) return offset + 2
                    if (offset <= 11) return offset + 3
                    if (offset <= 14) return offset + 4
                    return out.length
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (trimmed.length <= 11) {
                    if (offset <= 3) return offset
                    if (offset <= 7) return offset - 1
                    if (offset <= 11) return offset - 2
                    if (offset <= 14) return offset - 3
                    return trimmed.length
                } else {
                    if (offset <= 2) return offset
                    if (offset <= 6) return offset - 1
                    if (offset <= 10) return offset - 2
                    if (offset <= 15) return offset - 3
                    if (offset <= 18) return offset - 4
                    return trimmed.length
                }
            }
        }

        return TransformedText(AnnotatedString(out), offsetMapping)
    }
}
