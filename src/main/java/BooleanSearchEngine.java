import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    Map<String, List<PageEntry>> wordIndex = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException, NullPointerException {
        for (File path : pdfsDir.listFiles()) {
            PdfDocument doc = new PdfDocument(new PdfReader(path));
            for (int i = 1; i < doc.getNumberOfPages(); i++) {
                PdfPage page = doc.getPage(i);
                String pdfName = doc.getDocumentInfo().getTitle();
                String text = PdfTextExtractor.getTextFromPage(page);
                String[] words = text.split("\\P{IsAlphabetic}+");
                Map<String, Integer> freqs = getWordCount(words);
                indexWords(wordIndex, freqs, pdfName, i);
            }
        }
    }

    private Map<String, Integer> getWordCount(String[] words) {
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }
            map.put(word.toLowerCase(), map.getOrDefault(word, 0) + 1);
        }
        return map;
    }

    private void indexWords (Map<String, List<PageEntry>> wordIndex, Map<String, Integer> freqs, String pdfName, int pageNumber) {
        for (Map.Entry<String, Integer> entry :
                freqs.entrySet()) {
            String word = entry.getKey();
            int wordFrequency = entry.getValue();
            PageEntry pageEntry = new PageEntry(pdfName, pageNumber, wordFrequency);
            if (!wordIndex.containsKey(word)) {
                wordIndex.put(word, new ArrayList<>(List.of(pageEntry)));
            } else {
                wordIndex.get(word).add(pageEntry);
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        List<PageEntry> result;
        if (wordIndex.containsKey(word)) {
            result = wordIndex.get(word);
            Collections.sort(result);
        } else {
            result = new ArrayList<>(List.of(new PageEntry("НЕ НАЙДЕНО ФАЙЛОВ PDF, СОДЕРЖАЩИХ СЛОВО: " + word, 0, 0)));
        }
        return result;
    }
}
