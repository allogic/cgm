package com.cgm;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Simple question app.
 */
public class App
{
    /*
     * Various typedefs.
     */
    public class Questions extends HashMap<String, ArrayList<String>>{}
    public class FmtException extends Exception{}

    /*
     * Class member variables.
     */
    private Scanner scanner;
    private Questions questions;

    /*
     * Getter.
     */
    public Questions GetQuestions()
    {
        return questions;
    }

    /*
     * Constructor.
     */
    public App()
    {
        questions = new Questions();
    }

    /*
     * CLI.
     */
    public void Run(InputStream stream)
    {
        scanner = new Scanner(stream);

        System.out.printf("Question App\n");
        System.out.printf("\tType \"add\" to insert questions and answers.\n");
        System.out.printf("\tType \"ask\" to ask a question and view available answers.\n");
        System.out.printf("\tType \"end\" to exit the app.\n");

        while (true)
        {
            System.out.printf("$> ");

            final String line = scanner.nextLine();

            int resultCode = 0;

            try
            {
                switch (line)
                {
                    case "add":  resultCode = PromptAddQuestion(); break;
                    case "ask":  resultCode = PromptAskQuestion(); break;
                    case "end":                                    return;
                }
            }
            catch (FmtException e)
            {
                System.out.printf(e.getMessage());
            }

            if (resultCode != 0)
                System.out.printf("Something went wrong!\n");
        }
    }

    /*
     * Various parsers.
     */
    private int PromptAddQuestion() throws FmtException
    {
        System.out.printf("Enter a question and optional answers in the following format without angle brackets:\n");
        System.out.printf("<question>? \"<answer1>\" \"<answer2>\" \"<answerX>\"\n");
        System.out.printf("$> ");

        final String line = scanner.nextLine();

        //Todo: Implement further regex format checks!

        String question = "";
        ArrayList<String> answers = new ArrayList<>();

        // Parse question
        final int questionEnd = line.indexOf('?');
        if (questionEnd < 0)
            return 1;
        question = line.substring(0, Math.min(255, questionEnd));

        // Parse answers
        final Matcher answerMatcher = Pattern.compile("\"([^\"]*)\"").matcher(line);
        while (answerMatcher.find())
        {
            String answer = answerMatcher.group(1);
            answers.add(answer.substring(0, Math.min(255, answer.length())));
        }

        // Questions must have at least one answer
        if (answers.size() == 0)
            return 1;

        AddQuestion(question, answers);

        return 0;
    }
    private int PromptAskQuestion() throws FmtException
    {
        System.out.printf("Enter a question in the following format without angle brackets:\n");
        System.out.printf("<question>?\n");
        System.out.printf("$> ");

        final String line = scanner.nextLine();

        //Todo: Implement further regex format checks!

        String question = "";

        // Parse question
        final int questionEnd = line.indexOf('?');
        if (questionEnd < 0)
            return 1;
        question = line.substring(0, Math.min(255, questionEnd));

        AskQuestion(question);

        return 0;
    }

    /*
     * Internal question map state management.
     */
    private void AddQuestion(String question, List<String> answers)
    {
        questions.put(question, new ArrayList<>(answers));
    }
    private void AskQuestion(String question)
    {
        ArrayList<String> answers = questions.get(question);

        if (answers == null)
        {
            System.out.printf("the answer to life, universe and everything is 42\n");
        }
        else
        {
            for (String answer : answers)
                System.out.printf("\t%s\n", answer);
        }
    }
}
