package com.vlv.libraries.soulcasting

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.writeTo
import java.io.File


data class ColorClass(
    val name: String,
    val value: String
)

class FileProcessor(
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {

    @OptIn(KotlinPoetKspPreview::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val text = File("src/test/resources/res/values/colors.xml")

        var lines = listOf<ColorClass>()

        text.inputStream().bufferedReader().use {
            lines = it.lineSequence().asSequence().filter { it.contains("<color") }.map {
                val array = it.trim().removePrefix("<color name=\"").removeSuffix("</color>").split("\">#")
                array
            }.filter { it.size == 2 }.map {
                ColorClass(it[0], it[1])
            }.toList()
        }

        FileSpec.scriptBuilder(
            "ThemeKt",
            "com.vlv.libraries.data"
        ).apply {
            addCode(
                """
import androidx.compose.ui.graphics.Color
                    
object Test {
    ${lines.map { color -> "val ${color.name} = Color(0x${if(color.value.length == 6) "${color.value}FF" else color.value})"  }
        .joinToString(separator = "\n") { it }
    }
}
                """.trimIndent()
            )
//            addType(
//                TypeSpec.objectBuilder("ThemeAbc")
//                    .apply {
//                        lines.forEach { color ->
//                            addProperty(
//                                PropertySpec.builder(
//                                    color.name,
//                                    String::class
//                                )
//                                    .initializer("\"${color.value}\"")
//                                    .build()
//                            )
//                        }
//                    }
//                    .build()
//            )
        }.build().writeTo(codeGenerator, Dependencies(true))

        return emptyList()
    }

}

class FileProcessProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return FileProcessor(environment.codeGenerator)
    }

}