package data;
public class SentenceData
{
	//(VBD, VBN, VBG, VBZ, VBP and VB) 
  private String originalSentence;
  private String normalizedSentence;
  private boolean hasPresentVerb;
  private boolean hasPastVerb;
  private String assignedCategory;
  private int originalCategory;
  private String fromArticle;
  //
  private boolean hasVBD;
  private boolean hasVBN;
  private boolean hasVBG;
  private boolean hasVBZ;
  private boolean hasVBP;
  private boolean hasVB;

  public SentenceData(String originalSentence)
  {
    this.originalSentence = originalSentence;
    this.normalizedSentence = "";
    this.hasPresentVerb = false;
    this.hasPastVerb = false;
    this.assignedCategory = "";
    this.originalCategory = -1;
    this.fromArticle = "";
    //
    this.hasVBD = false;
    this.hasVBN = false;
    this.hasVBG = false;
    this.hasVBZ = false;
    this.hasVBP = false;
    this.hasVB = false;
  }

  public String getFromArticle() {
    return this.fromArticle;
  }

  public void setFromArticle(String fromArticle) {
    this.fromArticle = fromArticle;
  }

  public String getAssignedCategory() {
    return this.assignedCategory;
  }

  public void setAssignedCategory(String assignedCategory) {
    this.assignedCategory = assignedCategory;
  }

  public boolean isHasPastVerb() {
    return this.hasPastVerb;
  }

  public void setHasPastVerb(boolean hasPastVerb) {
    this.hasPastVerb = hasPastVerb;
  }

  public boolean isHasPresentVerb() {
    return this.hasPresentVerb;
  }

  public void setHasPresentVerb(boolean hasPresentVerb) {
    this.hasPresentVerb = hasPresentVerb;
  }

  public String getNormalizedSentence() {
    return this.normalizedSentence;
  }

  public void setNormalizedSentence(String normalizedSentence) {
    this.normalizedSentence = normalizedSentence;
  }

  public int getOriginalCategory() {
    return this.originalCategory;
  }

  public void setOriginalCategory(int originalCategory) {
    this.originalCategory = originalCategory;
  }

  public String getOriginalSentence() {
    return this.originalSentence;
  }

  public void setOriginalSentence(String originalSentence) {
    this.originalSentence = originalSentence;
  }
  
  ////(VBD, VBN, VBG, VBZ, VBP and VB) 
  
  
  public boolean isHasVBD() {
	    return this.hasVBD;
  }

  public void setHasVBD(boolean hasVBD) {
	    this.hasVBD = hasVBD;
  }
  
  
  public boolean isHasVBN() {
	    return this.hasVBN;
}

public void setHasVBN(boolean hasVBN) {
	    this.hasVBN = hasVBN;
}

public boolean isHasVBG() {
    return this.hasVBG;
}

public void setHasVBG(boolean hasVBG) {
    this.hasVBG = hasVBG;
}

public boolean isHasVBZ() {
    return this.hasVBZ;
}

public void setHasVBZ(boolean hasVBZ) {
    this.hasVBZ = hasVBZ;
}

public boolean isHasVBP() {
    return this.hasVBP;
}

public void setHasVBP(boolean hasVBP) {
    this.hasVBP = hasVBP;
}

public boolean isHasVB() {
    return this.hasVB;
}

public void setHasVB(boolean hasVB) {
    this.hasVB = hasVB;
}




  
}