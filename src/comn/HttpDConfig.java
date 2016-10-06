package comn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpDConfig {
  private static Map<String, List<String>> configurationMap = new HashMap<String, List<String>>();
  private BufferedReader reader;
  private String fileName;

  public HttpDConfig(String fileName) {
    this.fileName = fileName;
    load();
  }

  public void load() {
    try {
      String line;
      String[] header;
      List<String> values;
      reader = new BufferedReader(new FileReader(fileName));
      while ((line = reader.readLine()) != null) {
        header = line.trim().split(":?\\s+");
        if (header[0].isEmpty() || ( header[0].charAt(0) == '#' ) || ( header.length == 1 )) {
          continue;
        }
        values = new ArrayList<String>();
        for (int i = 1; i < header.length; i++) {
          values.add(header[i]);
        }
        configurationMap.put(header[0], values);
      }
    } catch (IOException e) {
      System.out.println("Error with httpd configuration file.");
    }
  }

  public String lookUp(String headerName) {
    if (configurationMap.isEmpty()) {
      return "Please parse the file.";
    } else if (configurationMap.containsKey(headerName)) {
      return configurationMap.get(headerName).toString();
    }
    return "No header with that name found in current file.";
  }

  public String getAllHeaders() {
    if (configurationMap.isEmpty()) {
      return "Please parse the file";
    }
    String allHeaders = "";
    for (Map.Entry<String, List<String>> entry : configurationMap.entrySet()) {
      allHeaders += entry.getKey() + "=> " + entry.getValue().toString() + "\n";
    }
    return allHeaders;
  }

  public  Map<String, List<String>>  getConfigurationMap() {

    return configurationMap;
  }

  public void setConfigurationMap(Map<String, List<String>> configurationMap) {
    this.configurationMap = configurationMap;
  }

  public static String getAccessFileName(){
    return configurationMap.get("AccessFileName").get(0);
  }
}
