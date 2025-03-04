package utils;

class Stemmer
{
  private char[] b;
  private int i;
  private int i_end;
  private int j;
  private int k;
  private static final int INC = 50;
  
  public Stemmer()
  {
    this.b = new char[50];
    this.i = 0;
    this.i_end = 0;
  }
  
  public void addWord(String word)
  {
    for (int i = 0; i < word.length(); i++) {
      add(word.charAt(i));
    }
  }
  
  public void add(char ch)
  {
    if (this.i == this.b.length)
    {
      char[] new_b = new char[this.i + 50];
      for (int c = 0; c < this.i; c++) {
        new_b[c] = this.b[c];
      }
      this.b = new_b;
    }
    this.b[(this.i++)] = ch;
  }
  
  public void add(char[] w, int wLen)
  {
    if (this.i + wLen >= this.b.length)
    {
      char[] new_b = new char[this.i + wLen + 50];
      for (int c = 0; c < this.i; c++) {
        new_b[c] = this.b[c];
      }
      this.b = new_b;
    }
    for (int c = 0; c < wLen; c++) {
      this.b[(this.i++)] = w[c];
    }
  }
  
  public String toString()
  {
    return new String(this.b, 0, this.i_end);
  }
  
  public int getResultLength()
  {
    return this.i_end;
  }
  
  public char[] getResultBuffer()
  {
    return this.b;
  }
  
  private final boolean cons(int i)
  {
    switch (this.b[i])
    {
    case 'a': 
    case 'e': 
    case 'i': 
    case 'o': 
    case 'u': 
      return false;
    case 'y': 
      return i == 0;
    }
    return true;
  }
  
  private final int m()
  {
    int n = 0;
    int i = 0;
    for (;;)
    {
      if (i > this.j) {
        return n;
      }
      if (!cons(i)) {
        break;
      }
      i++;
    }
    i++;
    for (;;)
    {
      if (i > this.j) {
        return n;
      }
      if (!cons(i))
      {
        i++;
      }
      else
      {
        i++;
        n++;
        for (;;)
        {
          if (i > this.j) {
            return n;
          }
          if (!cons(i)) {
            break;
          }
          i++;
        }
        i++;
      }
    }
  }
  
  private final boolean vowelinstem()
  {
    for (int i = 0; i <= this.j; i++) {
      if (!cons(i)) {
        return true;
      }
    }
    return false;
  }
  
  private final boolean doublec(int j)
  {
    if (j < 1) {
      return false;
    }
    if (this.b[j] != this.b[(j - 1)]) {
      return false;
    }
    return cons(j);
  }
  
  private final boolean cvc(int i)
  {
    if ((i < 2) || (!cons(i)) || (cons(i - 1)) || (!cons(i - 2))) {
      return false;
    }
    int ch = this.b[i];
    if ((ch == 119) || (ch == 120) || (ch == 121)) {
      return false;
    }
    return true;
  }
  
  private final boolean ends(String s)
  {
    int l = s.length();
    int o = this.k - l + 1;
    if (o < 0) {
      return false;
    }
    for (int i = 0; i < l; i++) {
      if (this.b[(o + i)] != s.charAt(i)) {
        return false;
      }
    }
    this.j = (this.k - l);
    return true;
  }
  
  private final void setto(String s)
  {
    int l = s.length();
    int o = this.j + 1;
    for (int i = 0; i < l; i++) {
      this.b[(o + i)] = s.charAt(i);
    }
    this.k = (this.j + l);
  }
  
  private final void r(String s)
  {
    if (m() > 0) {
      setto(s);
    }
  }
  
  private final void step1()
  {
    if (this.b[this.k] == 's') {
      if (ends("sses")) {
        this.k -= 2;
      } else if (ends("ies")) {
        setto("i");
      } else if (this.b[(this.k - 1)] != 's') {
        this.k -= 1;
      }
    }
    if (ends("eed"))
    {
      if (m() > 0) {
        this.k -= 1;
      }
    }
    else if (((ends("ed")) || (ends("ing"))) && (vowelinstem()))
    {
      this.k = this.j;
      if (ends("at"))
      {
        setto("ate");
      }
      else if (ends("bl"))
      {
        setto("ble");
      }
      else if (ends("iz"))
      {
        setto("ize");
      }
      else if (doublec(this.k))
      {
        this.k -= 1;
        int ch = this.b[this.k];
        if ((ch == 108) || (ch == 115) || (ch == 122)) {
          this.k += 1;
        }
      }
      else if ((m() == 1) && (cvc(this.k)))
      {
        setto("e");
      }
    }
  }
  
  private final void step2()
  {
    if ((ends("y")) && (vowelinstem())) {
      this.b[this.k] = 'i';
    }
  }
  
  private final void step3()
  {
    if (this.k == 0) {
      return;
    }
    switch (this.b[(this.k - 1)])
    {
    case 'a': 
      if (ends("ational")) {
        r("ate");
      } else if (ends("tional")) {
        r("tion");
      }
      break;
    case 'c': 
      if (ends("enci")) {
        r("ence");
      } else if (ends("anci")) {
        r("ance");
      }
      break;
    case 'e': 
      if (ends("izer")) {
        r("ize");
      }
      break;
    case 'l': 
      if (ends("bli")) {
        r("ble");
      } else if (ends("alli")) {
        r("al");
      } else if (ends("entli")) {
        r("ent");
      } else if (ends("eli")) {
        r("e");
      } else if (ends("ousli")) {
        r("ous");
      }
      break;
    case 'o': 
      if (ends("ization")) {
        r("ize");
      } else if (ends("ation")) {
        r("ate");
      } else if (ends("ator")) {
        r("ate");
      }
      break;
    case 's': 
      if (ends("alism")) {
        r("al");
      } else if (ends("iveness")) {
        r("ive");
      } else if (ends("fulness")) {
        r("ful");
      } else if (ends("ousness")) {
        r("ous");
      }
      break;
    case 't': 
      if (ends("aliti")) {
        r("al");
      } else if (ends("iviti")) {
        r("ive");
      } else if (ends("biliti")) {
        r("ble");
      }
      break;
    case 'g': 
      if (ends("logi")) {
        r("log");
      }
      break;
    }
  }
  
  private final void step4()
  {
    switch (this.b[this.k])
    {
    case 'e': 
      if (ends("icate")) {
        r("ic");
      } else if (ends("ative")) {
        r("");
      } else if (ends("alize")) {
        r("al");
      }
      break;
    case 'i': 
      if (ends("iciti")) {
        r("ic");
      }
      break;
    case 'l': 
      if (ends("ical")) {
        r("ic");
      } else if (ends("ful")) {
        r("");
      }
      break;
    case 's': 
      if (ends("ness")) {
        r("");
      }
      break;
    }
  }
  
  private final void step5()
  {
    if (this.k == 0) {
      return;
    }
    switch (this.b[(this.k - 1)])
    {
    case 'a': 
      if (!ends("al")) {
        return;
      }
      break;
    case 'c': 
      if ((!ends("ance")) && 
        (!ends("ence"))) {
        return;
      }
      break;
    case 'e': 
      if (!ends("er")) {
        return;
      }
      break;
    case 'i': 
      if (!ends("ic")) {
        return;
      }
      break;
    case 'l': 
      if ((!ends("able")) && 
        (!ends("ible"))) {
        return;
      }
      break;
    case 'n': 
      if ((!ends("ant")) && 
        (!ends("ement")) && 
        (!ends("ment"))) {
        if (!ends("ent")) {
          return;
        }
      }
      break;
    case 'o': 
      if ((!ends("ion")) || (this.j < 0) || ((this.b[this.j] != 's') && (this.b[this.j] != 't'))) {
        if (!ends("ou")) {
          return;
        }
      }
      break;
    case 's': 
      if (!ends("ism")) {
        return;
      }
      break;
    case 't': 
      if ((!ends("ate")) && 
        (!ends("iti"))) {
        return;
      }
      break;
    case 'u': 
      if (!ends("ous")) {
        return;
      }
      break;
    case 'v': 
      if (!ends("ive")) {
        return;
      }
      break;
    case 'z': 
      if (!ends("ize")) {
        return;
      }
      break;
    case 'b': 
    case 'd': 
    case 'f': 
    case 'g': 
    case 'h': 
    case 'j': 
    case 'k': 
    case 'm': 
    case 'p': 
    case 'q': 
    case 'r': 
    case 'w': 
    case 'x': 
    case 'y': 
    default: 
      return;
    }
    if (m() > 1) {
      this.k = this.j;
    }
  }
  
  private final void step6()
  {
    this.j = this.k;
    if (this.b[this.k] == 'e')
    {
      int a = m();
      if ((a > 1) || ((a == 1) && (!cvc(this.k - 1)))) {
        this.k -= 1;
      }
    }
    if ((this.b[this.k] == 'l') && (doublec(this.k)) && (m() > 1)) {
      this.k -= 1;
    }
  }
  
  public void stem()
  {
    this.k = (this.i - 1);
    if (this.k > 1)
    {
      step1();step2();step3();step4();step5();step6();
    }
    this.i_end = (this.k + 1);this.i = 0;
  }
}
