package stemmer;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.StringTokenizer;
import java.util.Vector;

public class PaiceStemmer
{
  private Vector ruleTable;
  private int[] ruleIndex;
  private boolean preStrip;
  private String filerules;

  public PaiceStemmer(String rules, String pre)
  {
    this.ruleTable = new Vector();
    this.ruleIndex = new int[26];
    this.preStrip = false;
    this.filerules = rules;
    if (pre.equals("/p")) {
      this.preStrip = true;
    }
    ReadRules(this.filerules);
  }

  private void ReadRules(String stemRules)
  {
    int ruleCount = 0;
    int j = 0;
    try
    {
      FileReader fr = new FileReader(stemRules);
      BufferedReader br = new BufferedReader(fr);
      String line = " ";
      try {
        while ((line = br.readLine()) != null) {
          ruleCount++;
          j = 0;
          String rule = new String();
          rule = "";
          while ((j < line.length()) && (line.charAt(j) != ' ')) {
            rule = rule + line.charAt(j);
            j++;
          }
          this.ruleTable.addElement(rule);
        }
      } catch (Exception e) {
        System.err.println("File Error Durring Reading Rules" + e);
        System.exit(0);
      }
      try
      {
        fr.close();
      } catch (Exception e) {
        System.err.println("Error Closing File During Reading Rules");
      }
    } catch (Exception e) {
      System.err.println("Input File" + stemRules + "not found");
      System.exit(1);
    }

    char ch = 'a';
    for (j = 0; j < 25; j++) {
      this.ruleIndex[j] = 0;
    }

    for (j = 0; j < ruleCount - 1; j++)
      while (((String)this.ruleTable.elementAt(j)).charAt(0) != ch) {
        ch = (char)(ch + '\001');
        this.ruleIndex[charCode(ch)] = j;
      }
  }

  private int FirstVowel(String word, int last)
  {
    int i = 0;
    if ((i < last) && (!vowel(word.charAt(i), 'a'))) {
      i++;
    }
    if (i != 0) {
      while ((i < last) && (!vowel(word.charAt(i), word.charAt(i - 1)))) {
        i++;
      }
    }
    if (i < last) {
      return i;
    }
    return last;
  }

  private String stripSuffixes(String word)
  {
    int ruleok = 0;
    int Continue = 0;

    int pll = 0;

    String rule = "";
    String stem = "";

    boolean intact = true;

    stem = Clean(word.toLowerCase());

    pll = 0;

    while ((pll + 1 < stem.length()) && (stem.charAt(pll + 1) >= 'a') && (stem.charAt(pll + 1) <= 'z')) {
      pll++;
    }

    if (pll < 1) {
      Continue = -1;
    }

    int pfv = FirstVowel(stem, pll);

    int iw = stem.length() - 1;

    while (Continue != -1) {
      Continue = 0;
      char ll = stem.charAt(pll);
     // int prt;
      int prt;
      if ((ll >= 'a') && (ll <= 'z'))
        prt = this.ruleIndex[charCode(ll)];
      else {
        prt = -1;
      }
      if (prt == -1) {
        Continue = -1;
      }

      if (Continue != 0) {
        continue;
      }
      rule = (String)this.ruleTable.elementAt(prt);
      while (Continue == 0) {
        ruleok = 0;
        if (rule.charAt(0) != ll)
        {
          Continue = -1;
          ruleok = -1;
        }
        int ir = 1;
        iw = pll - 1;

        while (ruleok == 0) {
          if ((rule.charAt(ir) >= '0') && (rule.charAt(ir) <= '9'))
          {
            ruleok = 1; continue;
          }if (rule.charAt(ir) == '*')
          {
            if (intact) {
              ir += 1;
              ruleok = 1; continue;
            }
            ruleok = -1; continue;
          }
          if (rule.charAt(ir) != stem.charAt(iw))
          {
            ruleok = -1; continue;
          }if (iw <= pfv)
          {
            ruleok = -1; continue;
          }

          ir += 1;
          iw -= 1;
        }

        if (ruleok == 1)
        {
          int xl = 0;
          while ((rule.charAt(ir + xl + 1) < '.') || (rule.charAt(ir + xl + 1) > '>')) {
            xl++;
          }
          xl = pll + xl + 48 - rule.charAt(ir);

          if (pfv == 0)
          {
            if (xl < 1)
            {
              ruleok = -1;
            }

          }
          else if (((xl < 2 ? 1 : 0) | (xl < pfv ? 1 : 0)) != 0) {
            ruleok = -1;
          }

        }

        if (ruleok == 1)
        {
          intact = false;

          pll = pll + 48 - rule.charAt(ir);
          ir++;
          stem = stem.substring(0, pll + 1);

          while ((ir < rule.length()) && ('a' <= rule.charAt(ir)) && (rule.charAt(ir) <= 'z')) {
            stem = stem + rule.charAt(ir);
            ir++;
            pll++;
          }

          if (rule.charAt(ir) == '.') {
            Continue = -1; continue;
          }

          Continue = 1; continue;
        }

        prt += 1;
        rule = (String)this.ruleTable.elementAt(prt);
        if (rule.charAt(0) == ll)
          continue;
        Continue = -1;
      }

    }

    return stem;
  }

  private boolean vowel(char ch, char prev)
  {
    switch (ch) {
    case 'a':
    case 'e':
    case 'i':
    case 'o':
    case 'u':
      return true;
    case 'y':
      switch (prev) {
      case 'a':
      case 'e':
      case 'i':
      case 'o':
      case 'u':
        return false;
      }
      return true;
    }

    return false;
  }

  private int charCode(char ch)
  {
    return ch - 'a';
  }

  private String stripPrefixes(String str)
  {
    String[] prefixes = { "kilo", "micro", "milli", "intra", "ultra", "mega", "nano", "pico", "pseudo" };

    int last = prefixes.length;

    for (int i = 0; i < last; i++) {
      if ((str.startsWith(prefixes[i])) && (str.length() > prefixes[i].length())) {
        str = str.substring(prefixes[i].length());
        return str;
      }
    }
    return str;
  }

  private String Clean(String str)
  {
    int last = str.length();
    String temp = "";
    for (int i = 0; i < last; i++) {
      if (((str.charAt(i) >= 'a' ? 1 : 0) & (str.charAt(i) <= 'z' ? 1 : 0)) != 0) {
        temp = temp + str.charAt(i);
      }
    }
    return temp;
  }

  public String stripAffixes(String str)
  {
    if ((str.length() > 3) && (this.preStrip))
    {
      str = stripPrefixes(str);
    }
    if (str.length() > 3)
    {
      str = stripSuffixes(str);
    }
    return str;
  }

  public static void main(String[] args)
  {
    PaiceStemmer p = new PaiceStemmer(args[2], args[3]);

    StringTokenizer line = new StringTokenizer("");
    String output = "";
    String fileOut = args[1];
    String fileIn = args[0];
    try
    {
      FileWriter fw = new FileWriter(fileOut);
      BufferedWriter bw = new BufferedWriter(fw);

      FileReader fr = new FileReader(fileIn);
      BufferedReader br = new BufferedReader(fr);
      try
      {
        String text;
        while ((text = br.readLine()) != null) {
          line = new StringTokenizer(text);
          try {
            while (line.hasMoreTokens())
            {
              String word = new String();
              word = line.nextToken();
              bw.write(p.stripAffixes(word) + " ");
            }
            bw.newLine();
          } catch (Exception e) {
            System.err.println(e);
          }
        }
      } catch (Exception e) {
        System.err.println("File Error Durring Reading " + e);
        System.exit(0);
      }
      try
      {
        fr.close();
      } catch (Exception e) {
        System.err.println("Error Closing File During Reading " + e);
      }
      try {
        bw.close();
      } catch (Exception e) {
        System.err.println(e);
      }
    }
    catch (Exception e) {
      System.err.println("File Not Found " + args[0] + " exception " + e);
      System.exit(1);
    }
  }
}