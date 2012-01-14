package com.bulain.itext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;

public class ReaderTest {
    @SuppressWarnings({"unchecked"})
    @Test
    public void testReader() throws IOException {
        ClassPathResource resource = new ClassPathResource("pdf/formTemplate.pdf");
        PdfReader reader = new PdfReader(resource.getInputStream());
        AcroFields form = reader.getAcroFields();
        reader.close();

        Map<String, Object> fields = form.getFields();
        List<String> keys = new ArrayList<String>(fields.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            System.out.print(key + ": ");
            String[] options;
            String[] values;
            String[] states;
            switch (form.getFieldType(key)) {
                case AcroFields.FIELD_TYPE_CHECKBOX :
                    System.out.print("Checkbox");
                    states = form.getAppearanceStates(key);
                    System.out.println("\t" + Arrays.asList(states));
                    break;
                case AcroFields.FIELD_TYPE_COMBO :
                    System.out.println("Combobox");
                    options = form.getListOptionExport(key);
                    values = form.getListOptionDisplay(key);
                    System.out.println("\t" + Arrays.asList(values) + Arrays.asList(options));
                    break;
                case AcroFields.FIELD_TYPE_LIST :
                    System.out.println("List");
                    options = form.getListOptionExport(key);
                    values = form.getListOptionDisplay(key);
                    System.out.println("\t" + Arrays.asList(values) + Arrays.asList(options));
                    break;
                case AcroFields.FIELD_TYPE_NONE :
                    System.out.println("None");
                    break;
                case AcroFields.FIELD_TYPE_PUSHBUTTON :
                    System.out.println("Pushbutton");
                    break;
                case AcroFields.FIELD_TYPE_RADIOBUTTON :
                    System.out.println("Radiobutton");
                    states = form.getAppearanceStates(key);
                    System.out.println("\t" + Arrays.asList(states));
                    break;
                case AcroFields.FIELD_TYPE_SIGNATURE :
                    System.out.println("Signature");
                    break;
                case AcroFields.FIELD_TYPE_TEXT :
                    System.out.println("Text");
                    break;
                default :
                    System.out.println("?");
            }
        }
    }
}
