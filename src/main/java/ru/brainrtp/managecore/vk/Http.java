package ru.brainrtp.managecore.vk;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HttpsURLConnection;

public class Http {
    public Http() {
    }

    public static String sendPost(String url, Map<String, String> list) {
        try {
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection)obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "User-Agent");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            if (list != null) {
                String urlParameters = urlEncode(list);
                wr.writeBytes(urlParameters);
            }

            wr.flush();
            wr.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();

            String inputLine;
            while((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            return response.toString();
        } catch (Exception var8) {
            return null;
        }
    }

    static String urlEncode(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();

        Entry entry;
        for(Iterator var2 = map.entrySet().iterator(); var2.hasNext(); sb.append(String.format("%s=%s", urlEncodeUTF8(entry.getKey().toString()), urlEncodeUTF8(entry.getValue().toString())))) {
            entry = (Entry)var2.next();
            if (sb.length() > 0) {
                sb.append("&");
            }
        }

        return sb.toString();
    }

    private static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            throw new UnsupportedOperationException(var2);
        }
    }
}
