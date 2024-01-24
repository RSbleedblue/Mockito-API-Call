import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class FilePath {
    String path = "";
    ObjectMapper obj;
    public FilePath(String path){
        this.path = path;
        this.obj = new ObjectMapper();
    }
    public JsonNode init() throws IOException {
        JsonNode jsonNode = obj.readTree(new File(this.path));
        return jsonNode;
    }
}
