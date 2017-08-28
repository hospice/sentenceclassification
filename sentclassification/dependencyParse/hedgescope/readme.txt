HedgeScope - CRF based algorithm to automatically detect hedge cues and their 
scope in biological and clinical text

Available at: http://hedgescope.askhermes.org/ (web version also available 
at this address).

HedgeScope detects hedge cues and their scope in biological and clinical 
sentences. The algorithm can be run using different feature sets. The feature 
sets available are - 

  words -           the simplest feature set. The words in the sentence will 
                    be used to detect the scope of hedge cues.
  pos_words_crf -   all words except the words in the hedge cue phrase are 
                    replaced with their corresponding part of speech tag. The 
                    cue phrases are identified using a CRF based model. 
  pos_words_regex - all words except words in the hedge cue phrase  
                    are replaced with their corresponding part of speech tag. 
                    The cue phrases are identified using a regular expression 
                    based model.
  pos_cue_crf -     all words except the words in the hedge cue phrase are 
                    replaced with their corresponding part of speech tag. The 
                    words in the cue phrases are replaced with a custom tag 
                    'CUE'. The cue phrases are identified using a CRF based 
                    model.
  pos_cue_regex -   all words except the words in the hedge cue phrase are 
                    replaced with their corresponding part of speech tag. The 
                    words in the cue phrases are replaced with a custom tag 
                    'CUE'. The cue phrases are identified using a regular 
                    expression based model.
  
The output is in Abner's (http://pages.cs.wisc.edu/~bsettles/abner/) format. 
Each word in the entry sentence will be 
followed by a pipe symbol '|' followed by a tag. The possible tags are 
'O', 'B-S' and 'I-S'. If the word has an 'O' tag, then it is not a part of 
the hedge scope. If the word has a 'B-S' or 'I-S' tag, then it is a part 
of the hedge scope.
  
To run HedgeScope from command line, run - 

  java -jar hedgescope.jar feature_set sentence_in_quotes sentence_type

For example - 
  java -jar hedgescope.jar words "Protein ABC might interact with protein XYZ." biological
  
will give an output - 
  Protein|O  ABC|O  might|B-S  interact|I-S  with|I-S  protein|I-S  XYZ|I-S  .|O

You might have to increase the memory allocated to the Java virtual machine 
if you get a out of memory error. 

To use HedgeScope from your Java code, use the following example - 

  // Initialize these variables
  String sentence = ""; // The sentence to test
  String featureSet = "words"; // The feature set to use. Can be "words", "pos_words_crf", 
                               // "pos_words_regex", "pos_cue_crf" or "pos_cue_regex"
  String sentenceType = "unknown"; // The type of sentence. Can be "biological", 
                                   // "clinical" or "unknown"
  String baseDir = "./"; // Path to HedgeScope's home directory

  String suffix = edu.uwm.bionlp.hedgescope.Driver.getSuffix(sentenceType);
  HedgeTagger.HedgeDetectionFeatureSet featureSetName = edu.uwm.bionlp.hedgescope.Driver.getAlgorithmName(featureSet);
  String modelFileName = edu.uwm.bionlp.hedgescope.Driver.getModelName(featureSet, suffix);
  String cueDetectionFile = edu.uwm.bionlp.hedgescope.Driver.getCueDetectionFile(featureSet, suffix);
  HedgeTagger ht;
  if (cueDetectionFile.isEmpty()) {
    ht = new HedgeTagger(baseDir + modelFileName);
  } else {
    ht = new HedgeTagger(featureSetName, baseDir + modelFileName, baseDir + cueDetectionFile, baseDir + "left3words-wsj-0-18.tagger");
  }
  
  String str = new String(sentence.getBytes("US-ASCII")); // Optional, but useful.
  System.out.println(ht.tagHedgeScope(str).trim());

  

