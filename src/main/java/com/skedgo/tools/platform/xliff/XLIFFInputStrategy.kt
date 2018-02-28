package com.skedgo.tools.platform.xliff

import com.skedgo.tools.InputStringsStrategy
import com.skedgo.tools.model.TransUnit
import com.skedgo.tools.model.Translations
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.helpers.DefaultHandler
import rx.Single
import rx.subjects.BehaviorSubject
import java.io.InputStream
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

class XLIFFInputStrategy(var fileSourceType: String? = null) : InputStringsStrategy {

    val completed: BehaviorSubject<Translations> = BehaviorSubject.create()

    override fun createInputValues(input: InputStream): Single<Translations> =
            Single.fromCallable {
                val saxParser = initParser()
                parseInput(input, saxParser)
            }.flatMap { completed.first().toSingle() }

    private fun initParser(): SAXParser {
        val spf = SAXParserFactory.newInstance()
        spf.isNamespaceAware = true
        return spf.newSAXParser()
    }

    private fun parseInput(input: InputStream,
                           saxParser: SAXParser) {
        val xmlReader = saxParser.xmlReader
        xmlReader.contentHandler = ReaderHandler()
        xmlReader.parse(InputSource(input))
    }

    inner class ReaderHandler : DefaultHandler() {

        private val structure = Translations(mutableListOf())

        private var readingSource: Boolean = false
        private var readingNote: Boolean = false
        private var readingTarget: Boolean = false
        private var readingAltTrans: Boolean = false

        private var skipFile: Boolean = false

        private var id: String = ""
        private var source: String = ""
        private var note: String = ""
        private var target: String = ""

        override fun startDocument() {}

        override fun endDocument() {
            completed.onNext(structure)
        }

        override fun startElement(namespaceURI: String?, localName: String?, qName: String, atts: Attributes?) {
            when {
                qName == "file" && atts != null -> {
                    structure.targetLanguage = atts.getValue("target-language")
                    skipFile = shouldSkipFile(atts)
                }
                qName == "trans-unit" -> {
                    id = atts?.let { atts.getValue("id") } ?: ""
                    source = ""
                    note = ""
                    target = ""
                }
                qName == "source" -> readingSource = true
                qName == "note" -> readingNote = true
                qName == "target" -> readingTarget = true
                qName == "alt-trans" -> readingAltTrans = true
            }
        }

        private fun shouldSkipFile(atts: Attributes) =
                !(fileSourceType?.let { atts.getValue("original").endsWith(it) }
                        ?: true)

        override fun endElement(uri: String?, localName: String?, qName: String) {
            if (!skipFile && qName == "trans-unit") {
                structure.transUnits.add(TransUnit(id, source, resolveTarget(), note))
            }

            readingSource = false
            readingNote = false
            readingTarget = false
            readingAltTrans = false
        }

        private fun resolveTarget() =
                when (target.isEmpty()) {
                    true -> source
                    false -> target
                }

        override fun characters(ch: CharArray, start: Int, length: Int) {
            val characters = String(ch, start, length)
            when {
                readingSource -> source += characters
                readingNote -> note += characters
                readingTarget && !readingAltTrans -> target += characters.trim { it <= ' ' }
            }
        }
    }
}
