package com.example.kotlinquiz;

import java.io.IOException;
import java.net.URL;

public class Utility {

    public static void main(String[] args) throws Exception {
        Utility utility = new Utility();
        String absolutePath = utility.getAbsolutePath("./absolute/path/to/file");
    }

    public String getAbsolutePath(String relativeFilePath) throws IOException {
        URL url = this.getClass().getResource(relativeFilePath);
        return url.getPath();
    }
}

