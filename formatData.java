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
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.TypesafeMap.Key;


public class formatData {
	
    private static final String DATATRAINING = "/home/mtd/workspace/FDD/src/data_training.txt";
    private static final String CLASSTRAINING = "/home/mtd/workspace/FDD/src/class_training.txt";
    private static final String STOPWORDS = "/home/mtd/workspace/FDD/src/stopW.txt";
    
    public static String BRUTARFF = "/home/mtd/workspace/FDD/src/brut.arff";
    public static String STOPARFF = "/home/mtd/workspace/FDD/src/noStopW.arff";
    public static String LEMMARFF = "/home/mtd/workspace/FDD/src/lemma.arff";
    public static String MORFOARFF = "/home/mtd/workspace/FDD/src/morfo.arff";

    
    static String stopWords="";

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
    
    public static void writeBrut() throws IOException{
    	
        BufferedReader BR_DataTraining = null;
        FileReader FR_DataTraining = null;
        BufferedReader BW_ClassTraining = null;
        FileReader FR_ClassTraining = null;
        
        
        FR_DataTraining = new FileReader(DATATRAINING);
        BR_DataTraining = new BufferedReader(FR_DataTraining);
        FR_ClassTraining = new FileReader(CLASSTRAINING);
        BW_ClassTraining = new BufferedReader(FR_ClassTraining);
        
        BufferedWriter BW_BrutArff = null;
        FileWriter FW_BrutArff = null;
        
        FW_BrutArff = new FileWriter(BRUTARFF);
        BW_BrutArff = new BufferedWriter(FW_BrutArff);
        
        BW_BrutArff.write("@RELATION weka");
        BW_BrutArff.write("\n\n@ATTRIBUTE doc\t\tSTRING");
        BW_BrutArff.write("\n@ATTRIBUTE classX\t{0.0, 1.0}");
        BW_BrutArff.write("\n\n@DATA\n");
        
        String currentDoc;
        String currentClass;
        
        
        while ((currentDoc = BR_DataTraining.readLine()) != null && (currentClass = BW_ClassTraining.readLine()) != null) {
            //System.out.println(sCurrentLine);
            
            currentDoc = currentDoc.replace("\"", "");
            
            BW_BrutArff.write("\"");
            BW_BrutArff.write(currentDoc);
            BW_BrutArff.write("\"");
            BW_BrutArff.write(",");
            BW_BrutArff.write(currentClass);
            
            BW_BrutArff.newLine();
	}
        
        BW_BrutArff.close();
        FW_BrutArff.close();
        FR_DataTraining.close();
        BR_DataTraining.close();
        FR_ClassTraining.close();
       
    }
    
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
    
    public static void writeNoStopW() throws IOException{
    	BufferedReader BR_DataTraining = null;
        FileReader FR_DataTraining = null;
        BufferedReader BR_ClassTraining = null;
        FileReader FR_ClassTraining = null;
        
        
        FR_DataTraining = new FileReader(DATATRAINING);
        BR_DataTraining = new BufferedReader(FR_DataTraining);
        FR_ClassTraining = new FileReader(CLASSTRAINING);
        BR_ClassTraining = new BufferedReader(FR_ClassTraining);
        
        
        BufferedWriter BW_StopArff = null;
        FileWriter FW_StopArff = null;
        
        FW_StopArff = new FileWriter(STOPARFF);
        BW_StopArff = new BufferedWriter(FW_StopArff);
        
        BW_StopArff.write("@RELATION weka");
        BW_StopArff.write("\n\n@ATTRIBUTE doc\t\tSTRING");
        BW_StopArff.write("\n@ATTRIBUTE classX\t{0.0, 1.0}");
        BW_StopArff.write("\n\n@DATA\n");
        
        String currentDoc;
        String currentClass;
        String newDoc;
        
        
        
        while ((currentDoc = BR_DataTraining.readLine()) != null && (currentClass = BR_ClassTraining.readLine()) != null) {
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
        
        BW_StopArff.close();
        FW_StopArff.close();
        FR_DataTraining.close();
        BR_DataTraining.close();
        FR_ClassTraining.close();
        
    }
    
    public static void writeLemma() throws IOException{
    	
    	BufferedReader BR_DataTraining = null;
        FileReader FR_DataTraining = null;
        BufferedReader BR_ClassTraining = null;
        FileReader FR_ClassTraining = null;
        
        
        FR_DataTraining = new FileReader(DATATRAINING);
        BR_DataTraining = new BufferedReader(FR_DataTraining);
        FR_ClassTraining = new FileReader(CLASSTRAINING);
        BR_ClassTraining = new BufferedReader(FR_ClassTraining);
        
        BufferedWriter BW_LemmaArff = null;
        FileWriter FW_LemmaArff = null;
        
        FW_LemmaArff = new FileWriter(LEMMARFF);
        BW_LemmaArff = new BufferedWriter(FW_LemmaArff);
        
        BW_LemmaArff.write("@RELATION weka");
        BW_LemmaArff.write("\n\n@ATTRIBUTE doc\t\tSTRING");
        BW_LemmaArff.write("\n@ATTRIBUTE classX\t{0.0, 1.0}");
        BW_LemmaArff.write("\n\n@DATA\n");
        
        String currentDoc;
        String currentClass;
        String sCurrentWord;
        
        int m=0;
        
        while ((currentDoc = BR_DataTraining.readLine()) != null && (currentClass = BR_ClassTraining.readLine()) != null) {
        	
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
        
        BW_LemmaArff.close();
        FW_LemmaArff.close();
        FR_DataTraining.close();
        BR_DataTraining.close();
        FR_ClassTraining.close();
        BR_ClassTraining.close();
    }
    
    public static void writeMorfo() throws IOException{
    	BufferedReader BR_DataTraining = null;
        FileReader FR_DataTraining = null;
        BufferedReader BR_ClassTraining = null;
        FileReader FR_ClassTraining = null;
        
        
        FR_DataTraining = new FileReader(DATATRAINING);
        BR_DataTraining = new BufferedReader(FR_DataTraining);
        FR_ClassTraining = new FileReader(CLASSTRAINING);
        BR_ClassTraining = new BufferedReader(FR_ClassTraining);
        
        BufferedWriter BW_MorfoArff = null;
        FileWriter FW_MorfoArff = null;
        
        FW_MorfoArff = new FileWriter(MORFOARFF);
        BW_MorfoArff = new BufferedWriter(FW_MorfoArff);
        
        BW_MorfoArff.write("@RELATION weka");
        BW_MorfoArff.write("\n\n@ATTRIBUTE doc\t\tSTRING");
        BW_MorfoArff.write("\n@ATTRIBUTE classX\t{0.0, 1.0}");
        BW_MorfoArff.write("\n\n@DATA\n");
        
        String currentDoc;
        String currentClass;
        String currentword;
        int count=0;
        while ((currentDoc = BR_DataTraining.readLine()) != null && (currentClass = BR_ClassTraining.readLine()) != null) {
        	
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
    			BW_MorfoArff.write(currentClass);
    			BW_MorfoArff.newLine();
        }
        
        BW_MorfoArff.close();
        FW_MorfoArff.close();
        FR_DataTraining.close();
        BR_DataTraining.close();
        FR_ClassTraining.close();
        BR_ClassTraining.close();
        
        
    }
    
	public static void main(String[] args) throws IOException{
		
		
		formatData();
		//writeBrut();
		//writeNoStopW();
		//writeLemma();
		//writeMorfo();
	}
	
	
}
