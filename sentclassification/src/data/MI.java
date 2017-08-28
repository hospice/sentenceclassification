package data;

public class MI
  implements Comparable<MI>
{
  private String term;
  private double mi;

  public MI(String term, double mi)
  {
    this.term = term;
    this.mi = mi;
  }

  public double getMi() {
    return this.mi;
  }

  public String getTerm() {
    return this.term;
  }

  public int compareTo(MI other) {
    if (this.mi < other.mi)
      return 1;
    if (this.mi == other.mi) {
      return 0;
    }
    return -1;
  }
}