package com.cgm;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Test
{
    public static void main(String[] args)
    {
        App app = new App();

        byte[] commands = new StringBuilder()
                .append("add\n")
                .append("this is some foo? \"this\" \"is\" \"some\" \"bar\"\n")
                .append("ask\n")
                .append("this is some foo?\n")
                .append("end\n")
                .toString()
                .getBytes(StandardCharsets.UTF_8);

        app.Run(new ByteArrayInputStream(commands));

        App.Questions questions = app.GetQuestions();

        assert questions.size() == 1 : "Map size mismatch";
    }
}
