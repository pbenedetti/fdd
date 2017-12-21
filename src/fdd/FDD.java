/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fdd;
import edu.stanford.nlp.ling.CoreAnnotations;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.SentimentAnnotator;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.TypesafeMap.Key;


public class FDD {
	
    private static final String DATATRAINING = "/Users/paolabenedetti/NetBeansProjects/FDD/src/data_training.txt";
    private static final String CLASSTRAINING = "/Users/paolabenedetti/NetBeansProjects/FDD/src/class_training.txt";
    private static final String DATATEST = "/Users/paolabenedetti/NetBeansProjects/FDD/src/data_test.txt";
    private static final String STOPWORDS = "/Users/paolabenedetti/NetBeansProjects/FDD/src/stopW.txt";
    
    public static String BRUTARFF_TRAIN = "/Users/paolabenedetti/NetBeansProjects/FDD/src/brut_train.arff";
    public static String STOPARFF_TRAIN = "/Users/paolabenedetti/NetBeansProjects/FDD/src/noStopW_train.arff";
    public static String LEMMARFF_TRAIN = "/Users/paolabenedetti/NetBeansProjects/FDD/src/lemma_train.arff";
    public static String MORFOARFF_TRAIN = "/Users/paolabenedetti/NetBeansProjects/FDD/src/morfo_train.arff";

    public static String BRUTARFF_TEST = "/Users/paolabenedetti/NetBeansProjects/FDD/src/brut_test.arff";
    public static String STOPARFF_TEST = "/Users/paolabenedetti/NetBeansProjects/FDD/src/noStopW_test.arff";
    public static String LEMMARFF_TEST = "/Users/paolabenedetti/NetBeansProjects/FDD/src/lemma_test.arff";
    public static String MORFOARFF_TEST = "/Users/paolabenedetti/NetBeansProjects/FDD/src/morfo_test.arff";
    
    static String stopWords="";

    //create String stopWords
    public static void formatData() throws IOException{

	BufferedReader BR_StopWords = null;
        FileReader FR_StopWords = null;
        FR_StopWords = new FileReader(STOPWORDS);
        BR_StopWords = new BufferedReader(FR_StopWords);
        String currentStopW;
        String newDoc="";
        
        
    	int i=0;
        while ((currentStopW = BR_StopWords.readLine()) != null){
        	stopWords=stopWords.concat(currentStopW+" ");
            i++;
             
     	}
    }
    
	
    //Read file training and create file brutt.arff 
    public static void writeBrut(String tag) throws IOException{
    	
        BufferedReader BR_Data = null;
        FileReader FR_Data = null;
        
        BufferedReader BW_ClassTraining = null;
        FileReader FR_ClassTraining = null;
        
        BufferedWriter BW_BrutArff = null;
        FileWriter FW_BrutArff = null;
        
        if(tag.equals("test")){
            
            FR_Data = new FileReader(DATATEST);
            BR_Data = new BufferedReader(FR_Data);
            
            FW_BrutArff = new FileWriter(BRUTARFF_TEST);
            BW_BrutArff = new BufferedWriter(FW_BrutArff);
        
            
        }
        else{
            
            FR_Data = new FileReader(DATATRAINING);
            BR_Data = new BufferedReader(FR_Data);
            
            FR_ClassTraining = new FileReader(CLASSTRAINING);
            BW_ClassTraining = new BufferedReader(FR_ClassTraining);
        
            FW_BrutArff = new FileWriter(BRUTARFF_TRAIN);
            BW_BrutArff = new BufferedWriter(FW_BrutArff);
        }
        
        
        BW_BrutArff.write("@RELATION weka");
        BW_BrutArff.write("\n\n@ATTRIBUTE doc\t\tSTRING");
        BW_BrutArff.write("\n@ATTRIBUTE classX\t{0.0, 1.0}");
        BW_BrutArff.write("\n\n@DATA\n");
        
        String currentDoc;
        String currentClass;
        
        if(tag.equals("test")){
            while ((currentDoc = BR_Data.readLine()) != null) {
                //System.out.println(sCurrentLine);

                currentDoc = currentDoc.replace("\"", "");

                BW_BrutArff.write("\"");
                BW_BrutArff.write(currentDoc);
                BW_BrutArff.write("\"");
                BW_BrutArff.write(",");

                BW_BrutArff.write('?');
                BW_BrutArff.newLine();
            }
        
        }
        else{
            while ((currentDoc = BR_Data.readLine()) != null && (currentClass = BW_ClassTraining.readLine()) != null) {
                //System.out.println(sCurrentLine);

                currentDoc = currentDoc.replace("\"", "");

                BW_BrutArff.write("\"");
                BW_BrutArff.write(currentDoc);
                BW_BrutArff.write("\"");
                BW_BrutArff.write(",");

               
                BW_BrutArff.write(currentClass);


                BW_BrutArff.newLine();
            }
            FR_ClassTraining.close();
        }
        
        BW_BrutArff.close();
        FW_BrutArff.close();
        FR_Data.close();
        BR_Data.close();
        
       
    }
    
    //check if the word is a stop word
    public static Boolean checkS(String word){
        StringTokenizer stop = new StringTokenizer(stopWords);
        
        for(int j = stop.countTokens(); j>0;j--){
   		 String stopW=(String)stop.nextElement();
   		 
   		 if(word.equals(stopW)){
   			 return true;
   		 }
   		 
   	 }
        return false;

    }
    
    //read each word in the document string
    public static String checkWord(String currentDoc) throws IOException{
    	
        StringTokenizer doc = new StringTokenizer(currentDoc);
        String W;
        String newDoc="";
        
         for(int h=doc.countTokens(); h>0; h--){
        	 W = (String) doc.nextElement();
        	 
        	 W = W.toLowerCase();
        	 if(checkS(W)==false){
        		 newDoc=newDoc.concat(W+" ");
        	 }
         }
        
        return newDoc;
    }
    
    //Read file training and create file noStopW.arff 
    public static void writeNoStopW(String tag) throws IOException{
    	BufferedReader BR_Data = null;
        FileReader FR_Data = null;
        
        BufferedReader BR_ClassTraining = null;
        FileReader FR_ClassTraining = null;
      
        BufferedWriter BW_StopArff = null;
        FileWriter FW_StopArff = null;
        
        
        if(tag.equals("test")){
            FR_Data = new FileReader(DATATEST);
            BR_Data = new BufferedReader(FR_Data);
            
            FW_StopArff = new FileWriter(STOPARFF_TEST);
            BW_StopArff = new BufferedWriter(FW_StopArff);
            
        }
        else{
            
            FR_Data = new FileReader(DATATRAINING);
            BR_Data = new BufferedReader(FR_Data);
            
            FR_ClassTraining = new FileReader(CLASSTRAINING);
            BR_ClassTraining = new BufferedReader(FR_ClassTraining);
            
            FW_StopArff = new FileWriter(STOPARFF_TRAIN);
            BW_StopArff = new BufferedWriter(FW_StopArff);
            
        }
    
        BW_StopArff.write("@RELATION weka");
        BW_StopArff.write("\n\n@ATTRIBUTE doc\t\tSTRING");
        BW_StopArff.write("\n@ATTRIBUTE classX\t{0.0, 1.0}");
        BW_StopArff.write("\n\n@DATA\n");
        
        String currentDoc;
        String currentClass;
        String newDoc;
        
        
        if(tag.equals("test")){
            while ((currentDoc = BR_Data.readLine()) != null) {
                //System.out.println(sCurrentLine);

                currentDoc = currentDoc.replace("\"", "");

                String stopWords[]={"\"",",",".","!","£","$","&","/","(",")","=","\'","-","_","?",":",";","+","*","^","#","@","`","'","{","}","[","]","+","~","0","1","2","3","4","5","6","7","8","9","|"};
                for(int i=0;i<stopWords.length;i++){
                    if(currentDoc.contains(stopWords[i])){
                        //System.out.println(stopWords[i]);
                        currentDoc=currentDoc.replace(stopWords[i], "");
                    }
                }

               newDoc=checkWord(currentDoc);

                BW_StopArff.write("\"");
                BW_StopArff.write(newDoc);
                BW_StopArff.write("\"");
                BW_StopArff.write(",");

                BW_StopArff.write('?');


                BW_StopArff.newLine();
            }
        }
        else{

            while ((currentDoc = BR_Data.readLine()) != null && (currentClass = BR_ClassTraining.readLine()) != null) {
                //System.out.println(sCurrentLine);

                currentDoc = currentDoc.replace("\"", "");

                String stopWords[]={"\"",",",".","!","£","$","&","/","(",")","=","\'","-","_","?",":",";","+","*","^","#","@","`","'","{","}","[","]","+","~","0","1","2","3","4","5","6","7","8","9","|"};
                for(int i=0;i<stopWords.length;i++){
                    if(currentDoc.contains(stopWords[i])){
                        //System.out.println(stopWords[i]);
                        currentDoc=currentDoc.replace(stopWords[i], "");
                    }
                }

               newDoc=checkWord(currentDoc);

                BW_StopArff.write("\"");
                BW_StopArff.write(newDoc);
                BW_StopArff.write("\"");
                BW_StopArff.write(",");

                
                BW_StopArff.write(currentClass);

                BW_StopArff.newLine();
            }
        FR_ClassTraining.close();

        }
        BW_StopArff.close();
        FW_StopArff.close();
        FR_Data.close();
        BR_Data.close();
        
    }
	
	
    //Read file training and create file lemma.arff 
    public static void writeLemma(String tag) throws IOException{
    	
    	BufferedReader BR_Data = null;
        FileReader FR_Data = null;
        
        BufferedReader BR_ClassTraining = null;
        FileReader FR_ClassTraining = null;
         
        BufferedWriter BW_LemmaArff = null;
        FileWriter FW_LemmaArff = null;
        
        if(tag.equals("test")){
        
            FR_Data = new FileReader(DATATEST);
            BR_Data = new BufferedReader(FR_Data);
            
            FW_LemmaArff = new FileWriter(LEMMARFF_TEST);
            BW_LemmaArff = new BufferedWriter(FW_LemmaArff);
            
        }
        else{
        
            FR_Data = new FileReader(DATATRAINING);
            BR_Data = new BufferedReader(FR_Data);
            
            FR_ClassTraining = new FileReader(CLASSTRAINING);
            BR_ClassTraining = new BufferedReader(FR_ClassTraining);
            
            FW_LemmaArff = new FileWriter(LEMMARFF_TRAIN);
            BW_LemmaArff = new BufferedWriter(FW_LemmaArff);
            
        }
        
        BW_LemmaArff.write("@RELATION weka");
        BW_LemmaArff.write("\n\n@ATTRIBUTE doc\t\tSTRING");
        BW_LemmaArff.write("\n@ATTRIBUTE classX\t{0.0, 1.0}");
        BW_LemmaArff.write("\n\n@DATA\n");
        
        String currentDoc;
        String currentClass;
        String sCurrentWord;
        
        int m=0;
        if(tag.equals("test")){
            while ((currentDoc = BR_Data.readLine()) != null) {

                    String newDoc="";

                            String stopWords[]={"\"",",",".","!","£","$","&","/","(",")","=","\'","-","_","?",":",";","+","*","^","#","@","`","'","{","}","[","]","+","~","0","1","2","3","4","5","6","7","8","9","|"};
                for(int i=0;i<stopWords.length;i++){
                    if(currentDoc.contains(stopWords[i])){
                        //System.out.println(stopWords[i]);
                        currentDoc=currentDoc.replace(stopWords[i], "");
                    }
                }

               newDoc=checkWord(currentDoc);


                    Sentence s = new Sentence(newDoc);
                    newDoc="";
                    List<String> lemmas = null;
                    lemmas=s.lemmas();
                    BW_LemmaArff.write("\"");

                            for(int i=0;i<lemmas.size();i++){

                                    sCurrentWord=lemmas.get(i);
                                    newDoc=newDoc.concat(sCurrentWord+" ");


                            }

                    //System.out.println("aaaaaa:  "+m+" ->"+ newDoc);
                    //m++;
                    BW_LemmaArff.write(newDoc);
                    BW_LemmaArff.write("\"");
                    BW_LemmaArff.write(",");
                    
                    BW_LemmaArff.write('?');
                    BW_LemmaArff.newLine();
                    
            }
        }
        else{

            while ((currentDoc = BR_Data.readLine()) != null && (currentClass = BR_ClassTraining.readLine()) != null) {

                    String newDoc="";

                            String stopWords[]={"\"",",",".","!","£","$","&","/","(",")","=","\'","-","_","?",":",";","+","*","^","#","@","`","'","{","}","[","]","+","~","0","1","2","3","4","5","6","7","8","9","|"};
                for(int i=0;i<stopWords.length;i++){
                    if(currentDoc.contains(stopWords[i])){
                        //System.out.println(stopWords[i]);
                        currentDoc=currentDoc.replace(stopWords[i], "");
                    }
                }

               newDoc=checkWord(currentDoc);


                    Sentence s = new Sentence(newDoc);
                    newDoc="";
                    List<String> lemmas = null;
                    lemmas=s.lemmas();
                    BW_LemmaArff.write("\"");

                            for(int i=0;i<lemmas.size();i++){

                                    sCurrentWord=lemmas.get(i);
                                    newDoc=newDoc.concat(sCurrentWord+" ");


                            }

                    //System.out.println("aaaaaa:  "+m+" ->"+ newDoc);
                    //m++;
                    BW_LemmaArff.write(newDoc);
                    BW_LemmaArff.write("\"");
                    BW_LemmaArff.write(",");
                    
                     
                    BW_LemmaArff.write(currentClass);

                    BW_LemmaArff.newLine();
                    
            }
        BR_ClassTraining.close();
        }
        BW_LemmaArff.close();
        FW_LemmaArff.close();
        FR_Data.close();
        BR_Data.close();
        
    }
	
    //Read file training and create file morfo.arff 
    public static void writeMorfo(String tag) throws IOException{
    	
        BufferedReader BR_Data = null;
        FileReader FR_Data = null;
        
        BufferedReader BR_ClassTraining = null;
        FileReader FR_ClassTraining = null;
        
        BufferedWriter BW_MorfoArff = null;
        FileWriter FW_MorfoArff = null;
        
        if(tag.equals("test")){
        
            FR_Data = new FileReader(DATATEST);
            BR_Data = new BufferedReader(FR_Data);
        
            FW_MorfoArff = new FileWriter(MORFOARFF_TEST);
            BW_MorfoArff = new BufferedWriter(FW_MorfoArff);
            
        }
        else{
        
            FR_Data = new FileReader(DATATRAINING);
            BR_Data = new BufferedReader(FR_Data);
            
            FR_ClassTraining = new FileReader(CLASSTRAINING);
            BR_ClassTraining = new BufferedReader(FR_ClassTraining);
            
            FW_MorfoArff = new FileWriter(MORFOARFF_TRAIN);
            BW_MorfoArff = new BufferedWriter(FW_MorfoArff);
        
        }
      
        BW_MorfoArff.write("@RELATION weka");
        BW_MorfoArff.write("\n\n@ATTRIBUTE doc\t\tSTRING");
        BW_MorfoArff.write("\n@ATTRIBUTE classX\t{0.0, 1.0}");
        BW_MorfoArff.write("\n\n@DATA\n");
        
        String currentDoc;
        String currentClass;
        String currentword;
        int count=0;
        
        if(tag.equals("test")){
            while ((currentDoc = BR_Data.readLine()) != null) {

                    String newDoc="";

                            String stopWords[]={"\"",",",".","!","£","$","&","/","(",")","=","\'","-","_","?",":",";","+","*","^","#","@","`","'","{","}","[","]","+","~","0","1","2","3","4","5","6","7","8","9","|"};
                for(int i=0;i<stopWords.length;i++){
                    if(currentDoc.contains(stopWords[i])){
                        //System.out.println(stopWords[i]);
                        currentDoc=currentDoc.replace(stopWords[i], "");
                    }
                }

               newDoc=checkWord(currentDoc);


               // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
               Properties props = new Properties();
               props.setProperty("annotators", "tokenize, ssplit, pos");
               StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

               // read some text in the text variable
               // create an empty Annotation just with the given text
               Annotation document = new Annotation(newDoc);

               // run all Annotators on this text
               pipeline.annotate(document);

               List<CoreMap> sentences = document.get(SentencesAnnotation.class);
               newDoc="";

               System.out.println(count);
               count++;
               for(CoreMap sentence: sentences) {


                       // traversing the words in the current sentence
                       // a CoreLabel is a CoreMap with additional token-specific methods
                       for (CoreLabel token: sentence.get(TokensAnnotation.class)) {

                               // this is the text of the token
                               String word = token.get(TextAnnotation.class);

                               // this is the POS tag of the token
                               String pos = token.get(PartOfSpeechAnnotation.class);

                               //JJ:Adjective JJS:Adjective Superlative VB:Verb
                               if(pos.equals("JJ") || pos.equals("JJS") || pos.equals("VB")){
                                       newDoc=newDoc.concat(word+" ");
                               }
                       }
               }

                    //System.out.println("aaaaaa:  "+m+" ->"+ newDoc);
                    //m++;
                            BW_MorfoArff.write("\"");
                            BW_MorfoArff.write(newDoc);
                            BW_MorfoArff.write("\"");
                            BW_MorfoArff.write(",");

                            BW_MorfoArff.write('?');
                            BW_MorfoArff.newLine();
            }
         
        }
        
        else{    
            while ((currentDoc = BR_Data.readLine()) != null && (currentClass = BR_ClassTraining.readLine()) != null) {

                    String newDoc="";

                            String stopWords[]={"\"",",",".","!","£","$","&","/","(",")","=","\'","-","_","?",":",";","+","*","^","#","@","`","'","{","}","[","]","+","~","0","1","2","3","4","5","6","7","8","9","|"};
                for(int i=0;i<stopWords.length;i++){
                    if(currentDoc.contains(stopWords[i])){
                        //System.out.println(stopWords[i]);
                        currentDoc=currentDoc.replace(stopWords[i], "");
                    }
                }

               newDoc=checkWord(currentDoc);


               // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
               Properties props = new Properties();
               props.setProperty("annotators", "tokenize, ssplit, pos, sentiment");
               StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

               // read some text in the text variable
               // create an empty Annotation just with the given text
               Annotation document = new Annotation(newDoc);

               // run all Annotators on this text
               pipeline.annotate(document);

               List<CoreMap> sentences = document.get(SentencesAnnotation.class);
               newDoc="";

               System.out.println(count);
               count++;
               for(CoreMap sentence: sentences) {


                       // traversing the words in the current sentence
                       // a CoreLabel is a CoreMap with additional token-specific methods
                       for (CoreLabel token: sentence.get(TokensAnnotation.class)) {

                               // this is the text of the token
                               String word = token.get(TextAnnotation.class);
                               
                               // this is the POS tag of the token
                               String pos = token.get(PartOfSpeechAnnotation.class);

                               //JJ:Adjective JJS:Adjective Superlative VB:Verb
                               if(pos.equals("JJ") || pos.equals("JJS") || pos.equals("VB")){
                                       newDoc=newDoc.concat(word+" ");
                               }
                       }
               }

                    //System.out.println("aaaaaa:  "+m+" ->"+ newDoc);
                    //m++;
                            BW_MorfoArff.write("\"");
                            BW_MorfoArff.write(newDoc);
                            BW_MorfoArff.write("\"");
                            BW_MorfoArff.write(",");

                            BW_MorfoArff.write(currentClass);
                            BW_MorfoArff.newLine();
            }
            FR_ClassTraining.close();
            BR_ClassTraining.close();
            }
        BW_MorfoArff.close();
        FW_MorfoArff.close();
        FR_Data.close();
        BR_Data.close();
        
    }
    
    
	public static void main(String[] args) throws IOException{
		
		
		formatData();
               
                //TRAIN FILE ARFF
		writeBrut("train");
                System.out.println("brut train DONE");
		writeNoStopW("train");
                System.out.println("noStopW train DONE");
		writeLemma("train");
                System.out.println("lemma train DONE");
		writeMorfo("train");
                System.out.println("morfo train DONE");
                
                //TEST FILE ARFF
                writeBrut("test");
                System.out.println("brut test DONE");
		writeNoStopW("test");
                System.out.println("noStopW test DONE");
		writeLemma("test");
                System.out.println("lemma test DONE");
		writeMorfo("test");
                System.out.println("morfo test DONE");
                
	}
	
	
}

