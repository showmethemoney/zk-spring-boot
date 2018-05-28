package tw.com.lancer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import com.google.gson.Gson;

public class LCServiceRequestBtp {
    public String doHttpUrlConnectionAction(String desiredUrl, List<HashMap> params) throws Exception {
        URL url = null;
        BufferedReader reader = null;
        StringBuffer stringBuffer;

        try {
            // create the HttpURLConnection
            url = new URL(desiredUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");

            HashMap param = new HashMap();
            /*
             * HashMap param1 = new HashMap(); HashMap param2 = new HashMap();
             * 
             * param1.put("SENDERACCOUNT", "ADMIN"); param1.put("HOSTINFO", "host3");
             * param1.put("SENDERTENANTID", "NEO_001"); param1.put("SENDERPASSWORD", "ADMIN");
             * param1.put("USECASE", "UC_CORE_CURRENCY"); param1.put("SERVICEID", "UPDCURRENCY");
             * param1.put("BUCURRENCYID", "AUD"); param1.put("CURRENCYID", "AUD"); param1.put("CURRENCYNAME",
             * "瞉喳馳"); param1.put("ROUNDPATTERNID", "DEC02"); param1.put("REMARK", "GGG");
             * 
             * 
             * param2.put("SENDERACCOUNT", "ADMIN"); param2.put("HOSTINFO", "host1");
             * param2.put("SENDERTENANTID", "NEO_001"); param2.put("SENDERPASSWORD", "ADMIN");
             * param2.put("USECASE", "UC_TOOL_FT_SYSTEMINFO"); param2.put("SERVICEID", "QRYDATABASETYPE");
             * 
             * param.put("1", param1); param.put("2", param2);
             */
            int i = 0;
            for (HashMap aMap : params) {
                param.put("param" + i, aMap);
                i++;
            }
            Gson gson = new Gson();
            String gsonStr = gson.toJson(param);
            System.out.println("Input Json ==>" + gsonStr);
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(gsonStr);
            wr.flush();
            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuffer = new StringBuffer();

            String line = null;
            while ((line = reader.readLine()) != null) {
                byte[] b = line.getBytes();
                stringBuffer.append(new String(b, "UTF-8") + "\n");
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            // close the reader; this can throw an exception too, so
            // wrap it in another try/catch block.
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

}
