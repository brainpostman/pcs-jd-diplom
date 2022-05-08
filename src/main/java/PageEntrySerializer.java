import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class PageEntrySerializer extends StdSerializer<PageEntry> {


    protected PageEntrySerializer(Class<PageEntry> t) {
        super(t);
    }

    @Override
    public void serialize(PageEntry pageEntry, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("pdfName", pageEntry.getPdfName());
        jsonGenerator.writeNumberField("page", pageEntry.getPage());
        jsonGenerator.writeNumberField("count", pageEntry.getCount());
        jsonGenerator.writeEndObject();
    }
}
