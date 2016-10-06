package comn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class MimeType  {
  private String filename;
  private BufferedReader bufferedReader;
  private Map<String, String> mimeTypesMap = new HashMap<String, String>();

  public MimeType(String filename) {
    this.filename=filename;
    load();
  }

  public void load() {
    try {
      String line;
      String[] mimeType;
      bufferedReader = new BufferedReader(new FileReader(filename));
      while ((line = bufferedReader.readLine()) != null) {
        mimeType = line.trim().split(":?\\s+");
        if (mimeType[0].isEmpty() || mimeType[0].charAt(0) == '#')
          continue;
        for (int i = 1; i < mimeType.length; i++)
          mimeTypesMap.put(mimeType[i], mimeType[0]);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String lookUp(String extension) {
    if (mimeTypesMap.isEmpty()) {
      return "Contention error.";
    } else if (mimeTypesMap.containsKey(extension)) {
      return mimeTypesMap.get(extension).toString();
    }
    return "Content type not found.";
  }

  public Map<String, String> getMimeTypesMap() {
    return mimeTypesMap;
  }

  public void setMimeTypesMap(Map<String, String> mimeTypesMap) {
    this.mimeTypesMap = mimeTypesMap;
  }
}
