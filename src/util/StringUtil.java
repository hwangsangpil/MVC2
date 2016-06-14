package util;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
<p>
<code>StringUtil</code>�� String�쓽 handling怨� 愿��젴�맂 class�씠�떎.
<p>
*/

public class StringUtil
{

   /**
    * String�쓣 �씫�뼱 �븣�뙆踰녠낵 �닽�옄留� 紐⑥븘 return ('_', '-' �룷�븿)
    * @param s source String
    * @return �븣�뙆踰녹쓣 �젣�쇅�븯怨� 嫄몃젮吏� String
    */
    public static String alphaNumOnly(String s)
    {
        int i = s.length();
        StringBuffer stringbuffer = new StringBuffer(i);
        for(int j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_' || c == '-')
                stringbuffer.append(c);
        }

        return stringbuffer.toString();
        
        
    }

    /**
     * String�쓣 �씫�뼱 �븣�뙆踰녠낵 �닽�옄留� �엳�뒗吏� check ('_', '-'�룷�븿)
     * @param s source String
 	 */
    public static boolean isAlphaNumOnly(String s)
    {
        int i = s.length();
        for(int j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            if((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < '0' || c > '9') && c != '_' && c != '-')
                return false;
        }

        return true;
    }

    /**
     * String�쓣 �씫�뼱 �븣�뙆踰노쭔 �엳�뒗吏� check
     * @param s source String
     */
    public static boolean isAlphaOnly(String s)
    {
    	int i = s.length();
    	for(int j = 0; j < i; j++)
    	{
    		char c = s.charAt(j);
    		if((c < 'a' || c > 'z') && (c < 'A' || c > 'Z'))
    			return false;
    	}
    	
    	return true;
    	
    	
    }
    
	/**
	* Alphabet 臾몄옄�씤吏� 泥댄겕
	* @param ch 泥댄겕�븷 臾몄옄
	* @return Alphabet 臾몄옄�씠硫� true, 洹몃젃吏� �븡�쑝硫� false
	*/
	public static boolean isAlpha(char ch)
	{
		if( ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' )
			return true;
		else
			return false;
	}

	/**
     * String�쓣 �씫�뼱 �닽�옄留� �엳�뒗吏� check
     * @param s source String
 	 */
    public static boolean isNumOnly(String s)
    {
        int i = s.length();
        for(int j = 0; j < i; j++)
        {
            char ch = s.charAt(j);
            if( ch < '0' || ch > '9')
                return false;
        }

        return true;
    }
    
	/**
	* �닽�옄 臾몄옄�씤吏� 泥댄겕
	* @param ch 泥댄겕�븷 臾몄옄
	* @return �닽�옄 臾몄옄�씠硫� true, 洹몃젃吏� �븡�쑝硫� false
	*/
	public static boolean isNumeric(char ch)
	{
		if( ch >= '0' && ch <= '9')
			return true;
		else
			return false;
	}

	/**
     * String s�뿉�꽌 �뿰�냽�릺�뒗 space�뱾�쓣 �븯�굹濡� �븬異뺥븳 String�쑝濡� return
     * @param s source String
 	 */
    public static String normalizeWhitespace(String s)
    {
        StringBuffer stringbuffer = new StringBuffer();
        int i = s.length();
        boolean flag = false;
        for(int j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            switch(c)
            {
            case 9: // '\t'
            case 10: // '\n'
            case 13: // '\r'
            case 32: // ' '
                if(!flag)
                {
                    stringbuffer.append(' ');
                    flag = true;
                }
                break;

            default:
                stringbuffer.append(c);
                flag = false;
                break;
            }
        }

        return stringbuffer.toString();
    }

	/**
     * String s�뿉�꽌 character c媛� 紐� 媛쒓� �엳�뒗吏� return
     * @param s source String
     * @param c 李얠쓣 character
 	 */
    public static int numOccurrences(String s, char c)
    {
        int i = 0;
        int j = 0;
        int l;
        for(int k = s.length(); j < k; j = l + 1)
        {
            l = s.indexOf(c, j);
            if(l < 0)
                break;
            i++;
        }

        return i;
    }

	/**
     * String s�뿉�꽌 String s1�뿉 �룷�븿�릺�뒗 紐⑤뱺 char瑜� �젣嫄고븳 String�쑝濡� return
     * @param s source String
     * @param s1 �궘�젣�떆�궗 sub String
 	 */
    public static String removeCharacters(String s, String s1)
    {
        int i = s.length();
        StringBuffer stringbuffer = new StringBuffer(i);
        for(int j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            if(s1.indexOf(c) == -1)
                stringbuffer.append(c);
        }

        return stringbuffer.toString();
    }

	/**
     * String s�뿉�꽌 議댁옱�븯�뒗 space�뱾�쓣 紐⑤몢 �젣嫄고븳 String�쑝濡� return
     * @param s source String
 	 */
    public static String removeWhiteSpace(String s)
    {
        int i = s.length();
        StringBuffer stringbuffer = new StringBuffer(i);
        for(int j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            if(!Character.isWhitespace(c))
                stringbuffer.append(c);
        }

        return stringbuffer.toString();
    }

    /**
     * String target�쓽 arguments[0],arguments[1]..遺�遺꾩쓣 replacements[0],replacements[1]..�쑝濡� 諛붽씀�뼱 return
     * @param target source String
     * @param arguments 諛붾�뚯뼱吏� ���긽�쓽 String 諛곗뿴
     * @param replacements ��泥대맆 String 諛곗뿴
 	 */
    public static String replace( String target, String[] arguments, String[] replacements )
    {
        if( target == null || arguments == null || replacements == null ) return target;

        for( int index = 0; index < arguments.length; index++ )
        {
            target = replace( target, arguments[index], replacements[index] );
        }

        return target;
    }

    /**
     * String target�뿉 �룷�븿�릺�뼱 �엳�뒗 argument�쓣 replacement濡� 諛붽씀�뼱 return
     * @param target source String
     * @param argument old String
     * @param replacement new String
 	 */
    public static String replace( String target, String argument, String replacement )
    {
        if ( target == null || argument == null || replacement == null ) return target;

        int i = target.indexOf(argument);

        if ( i == -1 ) return target;

        StringBuffer targetSB = new StringBuffer(target);
        while (i != -1)
        {
            targetSB.delete( i, i + argument.length() );
            targetSB.insert( i, replacement );
            //check for any more
            i = targetSB.toString().indexOf(argument,i + replacement.length());
        }

        return targetSB.toString();
    }

    /**
     * String s�뿉 �엳�뒗 character c瑜� �씠�슜�븯�뿬 String�쓣 遺꾨━�븳�떎.
     * @param s source String
     * @param c String s瑜� 遺꾨━�븷 character
     * @return 遺꾨━�맂 String 諛곗뿴
 	 */
    public static String[] splitStringAtCharacter(String s, char c)
    {
        String as[] = new String[numOccurrences(s, c) + 1];
        splitStringAtCharacter(s, c, as, 0);
        return as;
    }

    protected static int splitStringAtCharacter(String s, char c, String as[], int i)
    {
        int j = 0;
        int k = i;
        int l = 0;
        int j1;
        for(int i1 = s.length(); l <= i1 && k < as.length; l = j1 + 1)
        {
            j1 = s.indexOf(c, l);
            if(j1 < 0)
                j1 = i1;
            as[k] = s.substring(l, j1);
            j++;
            k++;
        }

        return j;
    }

    /**
     * Convert a String to a boolean
	 * <p>
	 * ���냼臾몄옄 �긽愿��뾾�씠 "true","yes","ok","okay","on","1"�씤 寃쎌슦 true瑜� return�븳�떎.
     * @param data the thing to convert
     * @return the converted data
     */
    public static boolean string2Boolean(String data)
    {
    	if(data==null) return false;
        if (data.equalsIgnoreCase("true")) return true;
        if (data.equalsIgnoreCase("yes")) return true;
        if (data.equalsIgnoreCase("ok")) return true;
        if (data.equalsIgnoreCase("okay")) return true;
        if (data.equalsIgnoreCase("on")) return true;
        if (data.equalsIgnoreCase("1")) return true;
        if (data.equalsIgnoreCase("y")) return true;
        

        return false;
    }

    /**
     * Convert a String to an int
     * @param data the thing to convert
     * @return the converted data
     */
    public static int string2Int(String data)
    {
        try
        {
            return Integer.parseInt(data);
        }
        catch (NumberFormatException ex)
        {
            return 0;
        }
    }
    
	/**
	 * 臾몄옄�뿴�쓣 ArrayList濡� 蹂��솚 
	 * @param strValue	String
	 * @return List
	 */
    public static List string2ArrayList(String strValue) {
		
    	List arrResult = new ArrayList();
		strValue = StringUtil.nchk(strValue);
		
		if (strValue == null || strValue.equals("")) {
			return arrResult;
		}
		
		String [] arrBuff = strValue.split(",");
		
		if (arrBuff == null) {
			return arrResult;
		}
		
		for (int i=0; i<arrBuff.length; i++) {
			arrResult.add(arrBuff[i].trim());
		}
		
		return arrResult;
	}

    /**
     * Convert a String to a Hashtable
	 * <p>
	 * "key1=value1 key2=value2 .... " 援ъ“�쓽 string�쓣 Hashtable濡� 蹂��솚
     * @param data the thing to convert
     * @return the converted data
     */
    public static Map string2Hashtable(String data)
    {
        Map commands = new HashMap();

        data = normalizeWhitespace(data);
        String[] data_arr = splitStringAtCharacter(data, ' ');

        for (int i=0; i<data_arr.length; i++)
        {
            int equ_pos = data_arr[i].indexOf('=');
            String key = data_arr[i].substring(0, equ_pos);
            String value = data_arr[i].substring(equ_pos + 1);

            commands.put(key, value);
        }

        return commands;
    }

    /**
     * Convert a Hashtable to a Sting
	 * <p>
	 * "key1=value1 key2=value2 .... " 援ъ“�쓽 string�쑝濡� 蹂��솚
     * @param data the thing to convert
     * @return the converted data
     */
    public static String hashtable2String(Map commands)
    {
        Iterator it = commands.keySet().iterator();
        StringBuffer retcode = new StringBuffer();

        while (it.hasNext())
        {
            String key = "";
            String value = "";

            try
            {
                key = (String) it.next();
                value = (String) commands.get(key);

                retcode.append(key);
                retcode.append("=");
                retcode.append(value);
                retcode.append(" ");
            }
            catch (ClassCastException ex)
            {
            }
        }

        return retcode.toString().trim();
    }

    /**
     * String s�뿉 �엳�뒗 alphabet�쓣 紐⑤몢 �냼臾몄옄濡� 諛붽씀�뼱 return
     * @param s source String
 	 */
    public static String toLowerCase(String s)
    {
        int i;
        int j;
        char c;
label0:
        {
            i = s.length();
            for(j = 0; j < i; j++)
            {
                char c1 = s.charAt(j);
                c = Character.toLowerCase(c1);
                if(c1 != c)
                    break label0;
            }

            return s;
        }
        char ac[] = new char[i];
        int k;
        for(k = 0; k < j; k++)
            ac[k] = s.charAt(k);

        ac[k++] = c;
        for(; k < i; k++)
            ac[k] = Character.toLowerCase(s.charAt(k));

        String s1 = new String(ac, 0, i);
        return s1;
    }

    /**
     * String s�뿉 �엳�뒗 alphabet�쓣 紐⑤몢 ��臾몄옄濡� 諛붽씀�뼱 return
     * @param s source String
 	 */
    public static String toUpperCase(String s)
    {
        int i;
        int j;
        char c;
label0:
        {
            i = s.length();
            for(j = 0; j < i; j++)
            {
                char c1 = s.charAt(j);
                c = Character.toUpperCase(c1);
                if(c1 != c)
                    break label0;
            }

            return s;
        }
        char ac[] = new char[i];
        int k;
        for(k = 0; k < j; k++)
            ac[k] = s.charAt(k);

        ac[k++] = c;
        for(; k < i; k++)
            ac[k] = Character.toUpperCase(s.charAt(k));

        return new String(ac, 0, i);
    }

   /**
    * String s�뿉 �엳�뒗 sub string s1�쓣 �씠�슜�븯�뿬 String�쓣 遺꾨━�븳�떎.
    * @param s source String
    * @param s1 String s瑜� 遺꾨━�븷 sub string
    * @return 遺꾨━�맂 string�쓽 踰≫꽣
    */
    public static Vector tokenizer(String s, String s1)
    {
        if(s == null)
            return null;
        Vector vector = null;
        for(StringTokenizer stringtokenizer = new StringTokenizer(s, s1); stringtokenizer.hasMoreTokens(); vector.addElement(stringtokenizer.nextToken().trim()))
            if(vector == null)
                vector = new Vector();

        return vector;
    }

   /**
    * &, <, >, "瑜� &amp;amp;, &amp;lt;, &amp;gt;, &amp;quot; 濡� ��泥댄븳 string�쑝濡� 諛붽씀�뼱 以�
    * @param s source String
    */
    public static String escapeHtmlString(String s)
    {
        String s1 = s;
        if(s1 == null)
            return null;
        if(s1.indexOf(38, 0) != -1)
            s1 = replace(s1, "&", "&amp;");
        if(s1.indexOf(60, 0) != -1)
            s1 = replace(s1, "<", "&lt;");
        if(s1.indexOf(62, 0) != -1)
            s1 = replace(s1, ">", "&gt;");
        if(s1.indexOf(34, 0) != -1)
            s1 = replace(s1, "\"", "&quot;");
        if(s1.indexOf(13, 0) != -1)
            s1 = replace(s1, "\\n", "<br>");
       return s1;
    }

   /**
    * &amp;amp;, &amp;lt;, &amp;gt;, &amp;quot;瑜� &, <, >, " 濡� ��泥댄븳 string�쑝濡� 諛붽씀�뼱 以�
    * @param s source String
    */
    public static String reEscapeHtmlString(String s)
    {
        String s1 = s;
        if(s1 == null)
            return null;
		String[] arguments = {"&amp;","&lt;","&gt;","&quot;"};
		String[] replacements = {"&","<",">","\""};
        return replace(s1, arguments, replacements);
    }

    /**
     * character c濡� length留뚰겮 梨꾩썙吏� String�쓣 return
     * @param c string�쑝濡� 梨꾩썙吏� character
     * @param length �썝�븯�뒗 character 媛��닔
     * @return charracter c濡� length 媛��닔 留뚰겮 梨꾩썙吏� string
     */
    public static String fill( char c, int length )
    {
        if( length <= 0 ) return "";

        char[] ca = new char[length];
        for( int index = 0; index < length; index++ )
        {
            ca[index] = c;
        }

        return new String( ca );
    }

   /**
    * 二쇱뼱吏� length瑜� �쑀吏��븯湲� �쐞�빐 String s�뿉 character c瑜� �삤瑜몄そ�쑝濡� �뜤�똾�떎.
	* <p>
	* <pre>
	* StringUtil.padRight("hahahaha", '.', 14);
	* StringUtil.padRight("hihihi", '.', 14);
	* StringUtil.padRight("hohohohoho", '.', 14);

	* �� �떎�쓬怨� 媛숈� 寃곌낵瑜� 蹂댁뿬以� 寃껋씠�떎.

	* hahahaha.....
	* hihihi.......
	* hohohohoho...
	* </pre>
	* �쐞�� 媛숈씠 �씪�젙�븳 �궗�씠利덈줈 臾몃떒�쓣 援ъ꽦�븯怨좎옄 �븷 �븣 �쑀�슜�븷 寃� �엫
    * @param s source String
    * @param c String s�뿉 �뜤��吏� character
    * @param length return�맆 String�쓽 length
    */
    public static String padRight( String s, char c, int length )
    {
        return s + fill( c, length - s.length() );
    }

   /**
    * 二쇱뼱吏� length瑜� �쑀吏��븯湲� �쐞�빐 String s�뿉 character c瑜� �쇊履쎌쑝濡� �뜤�똾�떎.
	* <p>
	* <pre>
	* StringUtil.padRight("hahahaha", '.', 14);
	* StringUtil.padRight("hihihi", '.', 14);
	* StringUtil.padRight("hohohohoho", '.', 14);

	* �� �떎�쓬怨� 媛숈� 寃곌낵瑜� 蹂댁뿬以� 寃껋씠�떎.

	* .....hahahaha
	* .......hihihi
	* ...hohohohoho
	* </pre>
	* �쐞�� 媛숈씠 �씪�젙�븳 �궗�씠利덈줈 臾몃떒�쓣 援ъ꽦�븯怨좎옄 �븷 �븣 �쑀�슜�븷 寃� �엫
    * @param s source String
    * @param c String s�뿉 �뜤��吏� character
    * @param length return�맆 String�쓽 length
    */
    public static String padLeft( String s, char c, int length )
    {
        return fill( c, length - s.length() ) + s;
    }

    


    /**
     * comma 援щ텇�옄瑜� 媛�吏�怨� Array瑜� String�쑝濡� 蹂��솚�븳�떎.
     * <p>
	 * �삁瑜쇰뱾硫�<br>
	 * {"aaa","bbbb","cc"} ---> "aaa,bbbb,cc"
     */
    public static String toString( Object[] args )
    {
        return toString( args, "," );
    }

    /**
     * separator 援щ텇�옄瑜� 媛�吏�怨� Array瑜� String�쑝濡� 蹂��솚
     */
    public static String toString( Object[] args, String separator )
    {
        if( args == null ) return null;

        StringBuffer buf = new StringBuffer();

        for( int index = 0; index < args.length; index++ )
        {
            if( index > 0 ) buf.append( separator );

            if( args[index] == null ) buf.append( "" );
            else buf.append( args[index].toString() );
        }

        return buf.toString();
    }

    /**
     * separator 援щ텇�옄瑜� 媛�吏�怨� List瑜� String�쑝濡� 蹂��솚
     */
    public static String toString( List list, String separator )
    {
        StringBuffer buf = new StringBuffer();
        for( int index = 0; index < list.size(); index++ )
        {
            if( index > 0 ) buf.append( separator );
            buf.append( list.get( index ).toString() );
        }
        return buf.toString();
    }

    /**
     * separator 援щ텇�옄瑜� 媛�吏�怨� List瑜� String�쑝濡� 蹂��솚
     */
    public static String toString( List list, String mapname, String separator )
    {
        StringBuffer buf = new StringBuffer();
        for( int index = 0; index < list.size(); index++ )
        {
        	HashMap info = (HashMap)list.get( index );
        	
            if( index > 0 ) buf.append( separator );
            buf.append( info.get(mapname).toString() );
        }
        return buf.toString();
    }
    
    /**
    * �쟾�떖�맂 臾몄옄�뿴�쓣 src_enc 諛⑹떇�뿉�꽌 dest_enc 諛⑹떇�쑝濡� 蹂��솚�븳�떎.
    * @author 誘쇱꽑湲�
    *
    * @param String str           蹂��솚�떆�궗 臾몄옄�뿴
    * @param String src_enc       �썝�옒 臾몄옄�쓽 encoding諛⑹떇
    * @param String des_enc       蹂��솚�떆�궗 encoding諛⑹떇.
    *
    * @return String  desc_enc 諛⑹떇�쑝濡� 蹂��솚�맂 臾몄옄�뿴
    *
    * @throws UnsupportedEncodingException :  Encoding�씠 吏��썝�릺吏� �븡�뒗 臾몄옄�뿴 蹂��솚�떆
    */
    public static  String toConvert(String str, String src_enc, String dest_enc)  throws java.io.UnsupportedEncodingException
    {
        if (str == null)
            return "";
        else
            return new String( str.getBytes(src_enc), dest_enc );
    }

    /**
    * Null String�쓣 "" String�쑝濡� 諛붽퓭以��떎.
    * @author 誘쇱꽑湲�
    *
    * @param str   Null 臾몄옄�뿴
    *
    * @return "" 臾몄옄�뿴(null�씠 �븘�땺 寃쎌슦�뒗 蹂��솚�븷 臾몄옄�뿴�씠 洹몃�濡� 由ы꽩)
    */
    static public String NVL(String str)
    {
        if(str == null)
            return "";
        else
            return str.trim();
    }

    // 臾몄옄�뿴�씠 null�씤寃쎌슦 replace_str�쓣 Return�븳�떎.
    // �궗�슜 �삁) �뀒�씠釉붿쓽 <td>str</td>�뿉�꽌 str�씠 null�씤 寃쎌슦
    // replate_str�씠 &nbsp;濡� 吏��젙�븳�떎.
    /**
    * 臾몄옄�뿴�씠 null�씤寃쎌슦 replace_str�쓣 Return�븳�떎.
	* �궗�슜 �삁) �뀒�씠釉붿쓽 <td>str</td>�뿉�꽌 str�씠 null�씤 寃쎌슦
	* replate_str�씠 &nbsp;濡� 吏��젙�븳�떎.
    * @author 誘쇱꽑湲�
    *
    * @param str Null 臾몄옄�뿴
    * @param replace_str 蹂��솚�븷 臾몄옄�뿴
    * @return 蹂��솚�븷 臾몄옄�뿴
    */
    static public String NVL(String str, String replace_str)
    {
        if( str == null ||  str.length()<=0) return replace_str;
        else return str;
    }

    	

    /**
     * �닽�옄 �룷留룹쓣 ,援щ텇�옄濡� �몴�떆 
     * @author 沅뚯뿰�꽑
     * 
     * @param str Null 臾몄옄�뿴
     * @param replace_str 蹂��솚�븷 臾몄옄�뿴
     * @return 蹂��솚�븷 臾몄옄�뿴
     */
    public static String getToCommaInt(String stText){
    	if(stText == null || stText.trim().equals("")) return "";
	    String ch = "#,###,##0";
	    java.text.DecimalFormat df = new java.text.DecimalFormat(ch);
	    String stResult = df.format(Integer.parseInt(stText));
	    return stResult;
    }
    /**
     * CLOB �뜲�씠�꽣瑜� String�쑝濡� 蹂� 
     * @author 諛뺤쭊�떇
     * 
     * @param str Null 臾몄옄�뿴
     * @param replace_str 蹂��솚�븷 臾몄옄�뿴
     * @return 蹂��솚�븷 臾몄옄�뿴
     */
    public static String clobToString(Clob clob) 
	{
    	String clobString = "";
   		try 
		{
			
			
			if (clob != null)
			{	
				Reader reader = clob.getCharacterStream();		
						
				BufferedReader clobReader = new BufferedReader(reader);                	

				StringWriter clobWriter = new StringWriter();
    	   		char[] buffer = new char[1024];
   	    		int size = 0;
                	
   	    		while((size = clobReader.read(buffer, 0, 1024)) != -1) 
					clobWriter.write(buffer, 0, size);
			
				clobString = clobWriter.toString();

			}

			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
	   	} 
	return clobString;
	}
    
    /**
     * �궗�슜�옄媛� �꽑�깮�븳 湲몄씠留뚰겮 臾몄옄�뿴�쓣 蹂댁뿬二쇨퀬 洹� �씠�썑遺�遺꾩� �궗�슜�옄媛� �엯�젰�븳 �뒪�듃留곸쑝濡� ��泥� 
     * @author 諛뺤쭊�떇
     * 
     * @param str Null 臾몄옄�뿴
     * @param replace_str 蹂��솚�븷 臾몄옄�뿴
     * @return 蹂��솚�븷 臾몄옄�뿴
     */
    static public String cutString(String src, int str_length, String att_str)
	{
		int ret_str_length = 0;
        
		String ret_str = new String("");

		if (src == null)
		{
			return ret_str;
		}

		// �쁽�옱 �솚寃쎌쓽 Character length瑜� 援ы븳�떎.
		String tempMulLanChar = new String("媛�");
		int lanCharLength = tempMulLanChar.length();
        
		// Character媛� 以묎컙�뿉 �옒由ъ� �븡寃� �븯湲곗쐞�빐 �꽔�� 蹂��닔
		int multiLanCharIndex = 0;

		for (int i=0; i<src.length(); i++)
		{
			ret_str += src.charAt(i);
            
			if (src.charAt(i)>'~')
			{
				ret_str_length = ret_str_length + 2/lanCharLength;
				multiLanCharIndex++;
			}
			else
			{
				ret_str_length = ret_str_length + 1;
			}
			if(ret_str_length >= str_length && (multiLanCharIndex%lanCharLength) == 0  )
			{
				ret_str += nchk(att_str);
				break;
			}
		}

		return ret_str;
	}
    /**
     * NULL 媛믪쓣 ""濡� 蹂�寃�  
     * @author 諛뺤쭊�떇
     * 
     * @param str Null 臾몄옄�뿴
     * @param replace_str 蹂��솚�븷 臾몄옄�뿴
     * @return 蹂��솚�븷 臾몄옄�뿴
     */
    
    static public String nchk(String str)
	{
		if (str == null || str.equals(""))
			return "";
		else
			return str;
	}
    
    /**
     * NULL 媛믪쓣 ""濡� 蹂�寃�  
     * @author 諛뺤쭊�떇
     * 
     * @param obj Object
     * @param replace_str 蹂��솚�븷 臾몄옄�뿴
     * @return 蹂��솚�븷 臾몄옄�뿴
     */
    
    static public String nchk(Object obj)
	{
		if (obj == null)
			return "";
		else
			return obj.toString();
	}    
    /**
     * NULL 媛믪쓣 ""濡� 蹂�寃�  
     * @author 諛뺤쭊�떇
     * 
     * @param str Null 臾몄옄�뿴
     * @param replace_str 蹂��솚�븷 臾몄옄�뿴
     * @return 蹂��솚�븷 臾몄옄�뿴
     */
    
    static public String nchk(String str, String dstr)
	{
		if (str == null || str.equals("")) {
			if (dstr == null || dstr.equals("")) {
				return "";
			} else {
				return dstr;
			}
		} else {
			return str;
		}
	}
    
    /**
     * NULL 媛믪쓣 ""濡� 蹂�寃�  
     * @author 諛뺤쭊�떇
     * 
     * @param str Object
     * @param replace_str 蹂��솚�븷 臾몄옄�뿴
     * @return 蹂��솚�븷 臾몄옄�뿴
     */
    
    static public String nchk(Object obj, String dstr)
	{
		if (obj == null)
			if (dstr == null)
				return "";
			else
				return dstr;
		else
			return obj.toString();
	}
		
	/**
	 * right
	 *
	 * 二쇱뼱吏� 媛믪쓣 �삤瑜몄そ�뿉�꽌 遺��꽣 len留뚰겮 吏ㅻ씪�꽌 諛섑솚
	 *
	 * @param s �냼�뒪 �뒪�듃留�
	 * @param len �옄瑜� 湲몄씠
	 *
	 * @return 二쇱뼱吏� �뒪�듃留곸쓣 len留뚰겮 �삤瑜몄そ�뿉�꽌 �옄瑜� 媛�
	 */
	public static String right(String s, int len) {
	  if (s == null)
		return "";
	  int L = s.length();
	  if (L <= len)
		return s;
	  return s.substring(L - len, L);
	}

	/**
	 * right
	 *
	 * 二쇱뼱吏� 媛믪쓣 �쇊履쎌뿉�꽌 遺��꽣 len留뚰겮 吏ㅻ씪�꽌 諛섑솚
	 *
	 * @param s �냼�뒪 �뒪�듃留�
	 * @param len �옄瑜� 湲몄씠
	 *
	 * @return 二쇱뼱吏� �뒪�듃留곸쓣 len留뚰겮 �쇊履쎌뿉�꽌 �옄瑜� 媛�
	 */
	public static String left(String s, int len) {
	  if (s == null)
		return "";
	  if (s.length() <= len)
		return s;
	  return s.substring(0, len);
	}
	
	
	/**
	 * right
	 *
	 * 二쇱뼱吏� 媛믪쓣 �쇊履쎌뿉�꽌 遺��꽣 len留뚰겮 吏ㅻ씪�꽌 諛섑솚
	 *
	 * @param s �냼�뒪 �뒪�듃留�
	 * @param len �옄瑜� 湲몄씠
	 *
	 * @return 二쇱뼱吏� �뒪�듃留곸쓣 len留뚰겮 �쇊履쎌뿉�꽌 �옄瑜� 媛�
	 */
	static public String cutLeft(String s, int len, String replace_str) {
	  if (s == null){
		return "";
	  }else if (s.length() <= len){
		return s;
	  }else {
	  	return s.substring(0, len)+replace_str;
	  }
	}	
	
	/**
	 * �쁺臾� or �븳湲� or �닽�옄濡� �맂 臾몄옄�뿴�씤吏�  泥댄겕(�젙洹쒗몴�쁽�떇)
	 * 
	 * @param s	�냼�뒪 �뒪�듃留�
	 * @return	�젙洹쒗몴�쁽�떇�쓣 留뚯”�븯硫� true, 留뚯”�븯吏� �븡�쑝硫� false
	 */
	public static boolean isAlphaHangulNumOnly(String s) {
		
		Pattern pattern = Pattern.compile("^[a-zA-Z�꽦-�옡0-9]+");
		Matcher match = pattern.matcher(s);
		
		if(match.find()){	
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * �븳湲�濡쒕쭔 �맂 臾몄옄�뿴�씤吏� 泥댄겕(�젙洹쒗몴�쁽�떇)
	 * 
	 * @param s	�냼�뒪 �뒪�듃留�
	 * @return	�젙洹쒗몴�쁽�떇�쓣 留뚯”�븯硫� true, 留뚯”�븯吏� �븡�쑝硫� false
	 */
	public static boolean isHangulOnly(String s) {
		
		Pattern pattern = Pattern.compile("^[�꽦-�옡]+");
		Matcher match = pattern.matcher(s);
		
		if(match.find()){	
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * �씠硫붿씪 �삎�떇 泥댄겕 (�젙洹쒗몴�쁽�떇)
	 * @param s	�냼�뒪 �뒪�듃留�
	 * @return	�젙洹쒗몴�쁽�떇�쓣 留뚯”�븯硫� true, 留뚯”�븯吏� �븡�쑝硫� false
	 */
	public static boolean isEmail(String s) {
		
	    Pattern pattern = Pattern.compile("\\w+[@]\\w+\\.\\w+\\.*\\w*");
	    Matcher match = pattern.matcher(s);
	    return match.matches();
	}
	
	/**
	 * isAcceptedAlpha (�젙洹쒗몴�쁽�떇 - �븳湲��쓣 �젣�쇅�븳 �쁺臾�, �듅�닔臾몄옄 �뿀�슜, 怨듬갚 遺덊뿀)
	 * @param s �냼�뒪 �뒪�듃留�
	 * @return �젙洹쒗몴�쁽�떇�쓣 留뚯”�븯硫� true, 留뚯”�븯吏� �븡�쑝硫� false
	 */
	public static boolean isAcceptedAlpha(String s) {
		
		Pattern pattern = Pattern.compile("[^媛�-�옡\\s]");
	    Matcher match = pattern.matcher(s);
	    
		if(match.find()){	
			return true;
		}else{
			return false;
		}
	}
}






