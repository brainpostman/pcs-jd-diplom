public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    public String getPdfName() {
        return pdfName;
    }

    public int getPage() {
        return page;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return '{' + "pdfName='" + pdfName + ", page=" + page + ", count=" + count + '}';
    }

    @Override
    public int compareTo(PageEntry o) {
        int i = Integer.compare(o.getCount(), count);
        if (i != 0) return i;

        i = pdfName.compareTo(o.getPdfName());
        if (i != 0) return i;

        return Integer.compare(page, o.getPage());
    }
}
