# ABOUT THE FILE
Taken a .txt containing a list of documents and their ground truth, returns the corrispondent .arff file that can be then used with weka.
The type of .arff file created for classification are 3:
  - brut.arff : document strings remains axactly the same of those given by the source
  - noStop.arff : documents strings lightened by removing stop words and puntuaction symbols
  - lemma.arff : lemmatized document strings
  - morfo.arff : lemmatized document strings with morphosintactic analysis

Need the library at https://nlp.stanford.edu/ to perform lemmatization and morphosintactic analysis
