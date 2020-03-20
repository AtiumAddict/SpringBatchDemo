package com.atiumaddict.springbatchdemo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;

@Slf4j
@Component
public class JobUtils {

    /*
     * Global Values
     */
    public static int LINES_TO_SKIP_DEFAULT;

    /*
     * Directories
     */
    public static String BASEPATH_DIR;


    @Autowired
    public void setValues(
            @Value("${app.dir.basepath}") String basepath,
            @Value("${application.globalvalues.linestoskip}") int linesToSkip
                         ) {
        BASEPATH_DIR = basepath;
        LINES_TO_SKIP_DEFAULT = linesToSkip;
    }

    public static Resource getLatestResourceByFilePattern(String folderPath, String pattern) {
        Collection<File> files = FileUtils.listFiles(
                new File(folderPath),
                new WildcardFileFilter(pattern), null);

        if (files.size() > 0) {
            File newestFile = files.stream()
                    .sorted(Comparator.comparing(File::getName))
                    .reduce((first, second) -> second)
                    .get();
            return new FileSystemResource(folderPath
                    + File.separator + newestFile.getName());
        } else
            return null;
    }

    /*
     * Rest Methods
     */
    public static void executeSqlScript(String filePath, DataSource dataSource) {
        final Connection connection;
        try {
            connection = dataSource.getConnection();
            ScriptUtils.executeSqlScript(connection, new EncodedResource(new ClassPathResource(filePath), StandardCharsets.UTF_8)
                    //,true, true, "", null, null, null
                                        );
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Counts the lines of a file resource.
     */
    public static int countFileLines(Resource fileResource) throws IOException {
        String fileName = fileResource.getFile().getPath();
        int lineCount = 0;
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        while (reader.readLine() != null) {
            lineCount++;
        }
        reader.close();

        return lineCount;
    }

    /**
     * Counts the lines of a file.
     */
    public static int countFileLines(String fileName) throws IOException {
        int lineCount = 0;
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        while (reader.readLine() != null) {
            lineCount++;
        }
        reader.close();

        return lineCount;
    }

}
