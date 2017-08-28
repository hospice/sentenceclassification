package utils;


import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Utilities
{
  public void PrintHashSet(Set<String> theSet)
  {
    for (String element : theSet)
      System.out.println(element);
  }

  public void PrintWords(Map<String, Double> dictionary)
  {
    for (Map.Entry e : dictionary.entrySet())
      System.out.println((String)e.getKey() + ": " + e.getValue());
  }

  public double jaccardDistance(Map<String, Double> m1, Map<String, Double> m2)
  {
    if ((m1.size() == 0) || (m2.size() == 0)) {
      return 0.0D;
    }

    int intersection = 0;
    if (m1.size() < m2.size()) {
      for (String key : m1.keySet())
        if (m2.containsKey(key))
          intersection++;
    }
    else {
      for (String key : m2.keySet()) {
        if (m1.containsKey(key)) {
          intersection++;
        }
      }
    }
    int union = 0;
    Set unionSet = new HashSet();
    for (String key : m1.keySet()) {
      unionSet.add(key);
    }
    for (String key : m2.keySet()) {
      unionSet.add(key);
    }
    union = unionSet.size();

    double jaccardCoefficient = intersection / union;

    return 1.0D - jaccardCoefficient;
  }

  public double scalarProduct(Map<String, Double> m1, Map<String, Double> m2)
  {
    double scalarProduct = 0.0D;
    if ((m1.size() == 0) || (m2.size() == 0))
      return 0.0D;
    if (m1.size() < m2.size()) {
      for (String key : m1.keySet())
        if (m2.containsKey(key))
          scalarProduct += ((Double)m1.get(key)).doubleValue() * ((Double)m2.get(key)).doubleValue();
    }
    else {
      for (String key : m2.keySet()) {
        if (m1.containsKey(key))
          scalarProduct += ((Double)m1.get(key)).doubleValue() * ((Double)m2.get(key)).doubleValue();
      }
    }
    return scalarProduct;
  }
  
  /*public List<String> similarityMap(Map<String, Double> m1, Map<String, Double> m2)
  {
	List<String> simList = new ArrayList<String, String>();
    double scalarProduct = 0.0D;
    if ((m1.size() == 0) || (m2.size() == 0))
      return 0.0D;
    if (m1.size() < m2.size()) {
      for (String key : m1.keySet())
        if (m2.containsKey(key))
		  simList.add(key);
          scalarProduct += ((Double)m1.get(key)).doubleValue() * ((Double)m2.get(key)).doubleValue();
    }
    else {
      for (String key : m2.keySet()) {
        if (m1.containsKey(key))
		  simList.add(key);
          scalarProduct += ((Double)m1.get(key)).doubleValue() * ((Double)m2.get(key)).doubleValue();
      }
    }
    return simList;
  }*/

  public double cosineAngle(Map<String, Double> m1, Map<String, Double> m2)
  {
    double scalarProduct = scalarProduct(m1, m2);
    double length1 = 0.0D;
    double length2 = 0.0D;
    for (Number value : m1.values()) {
      length1 += value.doubleValue() * value.doubleValue();
    }
    for (Number value : m2.values()) {
      length2 += value.doubleValue() * value.doubleValue();
    }
    if ((length1 == 0.0D) || (length2 == 0.0D)) {
      return 0.0D;
    }
    double denominator = Math.sqrt(length1) * Math.sqrt(length2);
    return scalarProduct / denominator;
  }

  public double pairwiseSimilarity(List<Map<String, Double>> ss, int index1, int index2)
  {
    Map ht1 = (Map)ss.get(index1);
    Map ht2 = (Map)ss.get(index2);
    return cosineAngle(ht1, ht2);
  }

  public double pairwiseDistance(List<Map<String, Double>> ss, int index1, int index2)
  {
    return 1.0D - pairwiseSimilarity(ss, index1, index2);
  }
  
  
  public double pairwiseSimilarity(List<Map<String, Double>> ss, List<Map<String, Double>> st, int index1, int index2)
  {
    Map ht1 = (Map)ss.get(index1);
    Map ht2 = (Map)st.get(index2);
    return cosineAngle(ht1, ht2);
  }

  public double pairwiseDistance(List<Map<String, Double>> ss, List<Map<String, Double>> st, int index1, int index2)
  {
    return 1.0D - pairwiseSimilarity(ss, st, index1, index2);
  }

  public double distanceFromSet(List<Map<String, Double>> ss, Set<Integer> picked, int index)
  {
    double dist = 0.0D;
    for (Integer ind : picked) {
      dist += pairwiseDistance(ss, index, ind.intValue());
    }
    return dist;
  }

  public int mostSimilarSentence(Set<Integer> usefulSentenceID, List<Map<String, Double>> sparseSentences, Map<String, Double> reference)
  {
    double dist = (-1.0D / 0.0D);
    Integer MostSimilarSentenceID = null;

    for (Integer id : usefulSentenceID) {
      double ndist = cosineAngle((Map)sparseSentences.get(id.intValue()), reference);
      if (dist < ndist) {
        dist = ndist;
        MostSimilarSentenceID = id;
      }
    }
    return MostSimilarSentenceID.intValue();
  }

  private double f(double x)
  {
    if (x == 0.0D)
      return 0.0D;
    double denom = Math.pow(2.0D, x - 1.0D);
    return 1.0D / denom;
  }

  public double gainOf(List<Map<String, Double>> sparseSentences, int[] indices)
  {
    double gain = 0.0D;
    Map<String, Double> localDictionary = new HashMap();

    for (int ind : indices) {
      Map<String, Double> ht = (Map)sparseSentences.get(ind);

      if (ht.size() != 0) {
        for (String key : ht.keySet()) {
          Double count = (Double)localDictionary.get(key);
          localDictionary.put(key, Double.valueOf(count == null ? 1.0D : count.doubleValue() + 1.0D));
        }
      }
    }

    if (localDictionary.size() != 0) {
      for (Double itg : localDictionary.values()) {
        gain += f(itg.doubleValue());
      }
    }
    return gain;
  }

  public double gainOf(List<Map<String, Double>> sparseSentences, Set<Integer> indices) {
    double gain = 0.0D;
    Map<String, Double> localDictionary = new HashMap();

    for (Integer ind : indices) {
      Map<String, Double> ht = (Map)sparseSentences.get(ind.intValue());

      if (ht.size() != 0) {
        for (String key : ht.keySet()) {
          Double count = (Double)localDictionary.get(key);
          localDictionary.put(key, Double.valueOf(count == null ? 1.0D : count.doubleValue() + 1.0D));
        }
      }
    }

    if (localDictionary.size() != 0) {
      for (Double itg : localDictionary.values()) {
        gain += f(itg.doubleValue());
      }
    }
    return gain;
  }

  public double coverOf(List<Map<String, Double>> sparseSentences, int[] indices)
  {
    Map<String, Double> localDictionary = new HashMap();

    for (int ind : indices) {
      Map<String, Double> ht = (Map)sparseSentences.get(ind);

      if (ht.size() != 0) {
        for (String key : ht.keySet()) {
          Double count = (Double)localDictionary.get(key);
          localDictionary.put(key, Double.valueOf(1.0D));
        }
      }
    }

    return localDictionary.size();
  }

  public double coverOf(List<Map<String, Double>> sparseSentences, Set<Integer> indices) {
    Map<String, Double> localDictionary = new HashMap();

    for (Integer ind : indices) {
      Map<String, Double> ht = (Map)sparseSentences.get(ind.intValue());

      if (ht.size() != 0) {
        for (String key : ht.keySet()) {
          Double count = (Double)localDictionary.get(key);
          localDictionary.put(key, Double.valueOf(1.0D));
        }
      }
    }
    return localDictionary.size();
  }

  public double entropyOf(List<Map<String, Double>> sparseSentences, int[] indices)
  {
    double entropy = 0.0D;
    Map<String, Double> localDictionary = new HashMap();

    for (int ind : indices) {
      Map<String, Double> ht = (Map)sparseSentences.get(ind);

      if (ht.size() != 0) {
        for (String key : ht.keySet()) {
          Double count = (Double)localDictionary.get(key);
          localDictionary.put(key, Double.valueOf(count == null ? 1.0D : count.doubleValue() + 1.0D));
        }
      }
    }

    if (localDictionary.size() != 0) {
      double m = 0.0D;
      for (Double itg : localDictionary.values()) {
        m += itg.doubleValue();
      }

      for (Double itg : localDictionary.values()) {
        double p = itg.doubleValue() / m;

        entropy += -p * Math.log(p);
      }
      return entropy;
    }
    return 0.0D;
  }

  public double entropyOf(List<Map<String, Double>> sparseSentences, Set<Integer> indices)
  {
    double entropy = 0.0D;
    Map<String, Double> localDictionary = new HashMap();

    for (Integer ind : indices) {
      Map<String, Double> ht = (Map)sparseSentences.get(ind.intValue());

      if (ht.size() != 0) {
        for (String key : ht.keySet()) {
          Double count = (Double)localDictionary.get(key);
          localDictionary.put(key, Double.valueOf(count == null ? 1.0D : count.doubleValue() + 1.0D));
        }
      }
    }

    if (localDictionary.size() != 0) {
      double m = 0.0D;
      for (Double itg : localDictionary.values()) {
        m += itg.doubleValue();
      }
      for (Double itg : localDictionary.values()) {
        double p = itg.doubleValue() / m;
        entropy += -p * Math.log(p);
      }
      return entropy;
    }
    return 0.0D;
  }

  public double evaluateOrthogonality(List<Map<String, Double>> sparseSentences, int[] indices)
  {
    double innerProduct = 0.0D;
    int numCombinations = indices.length * (indices.length - 1) / 2;

    for (int i = 0; i < indices.length - 1; i++) {
      for (int j = i + 1; j < indices.length; j++) {
        innerProduct += scalarProduct((Map)sparseSentences.get(indices[i]), (Map)sparseSentences.get(indices[j]));
      }
    }
    return innerProduct / numCombinations;
  }

  public double evaluateJaccardDistance(List<Map<String, Double>> sparseSentences, int[] indices)
  {
    if (indices.length == 1) {
      return 1.0D;
    }
    double jaccardDistance = 0.0D;
    int numCombinations = indices.length * (indices.length - 1) / 2;
    for (int i = 0; i < indices.length - 1; i++) {
      for (int j = i + 1; j < indices.length; j++) {
        jaccardDistance += jaccardDistance((Map)sparseSentences.get(indices[i]), (Map)sparseSentences.get(indices[j]));
      }
    }
    return jaccardDistance / numCombinations;
  }

  public double evaluateCosineAngle(List<Map<String, Double>> sparseSentences, int[] indices)
  {
    if (indices.length == 1) {
      return 0.0D;
    }
    double cosine = 0.0D;
    int numCombinations = indices.length * (indices.length - 1) / 2;
    for (int i = 0; i < indices.length - 1; i++) {
      for (int j = i + 1; j < indices.length; j++) {
        cosine += cosineAngle((Map)sparseSentences.get(indices[i]), (Map)sparseSentences.get(indices[j]));
      }
    }
    return cosine / numCombinations;
  }

  public double evaluateCoverage(List<Map<String, Double>> sparseSentences, int[] indices, int numUniqueWords)
  {
    Map<String, Double> localDictionary = new HashMap();

    for (int ind : indices) {
      Map<String, Double> ht = (Map)sparseSentences.get(ind);

      if (ht.size() != 0) {
        for (String key : ht.keySet()) {
          localDictionary.put(key, Double.valueOf(1.0D));
        }
      }
    }

    int coveredWords = localDictionary.size();
    return coveredWords / numUniqueWords;
  }

  public double evaluateHumanComputerDistance(List<Map<String, Double>> humanSparseSentences, int[] humanIndices, List<Map<String, Double>> computerSparseSentences, int[] computerIndices)
  {
    double dist = 0.0D;
    for (int i : humanIndices) {
      double max = 4.9E-324D;
      for (int j : computerIndices) {
        double tmp = scalarProduct((Map)humanSparseSentences.get(i), (Map)computerSparseSentences.get(j));
        if (tmp > max)
          max = tmp;
      }
      dist += max;
    }
    return dist / humanIndices.length;
  }
}