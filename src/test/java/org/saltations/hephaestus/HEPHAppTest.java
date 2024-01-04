package org.saltations.hephaestus;

import com.google.common.base.StandardSystemProperty;
import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
@DisplayName("HEPH Command Line")
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HEPHAppTest
{
    @BeforeAll
    void cleanOutHEPHTemplateFolder() throws IOException
    {
        var usersTemplateRootFolder = Paths.get(StandardSystemProperty.USER_HOME.value(), ".heph", "test", "templates");

        // If the folder already exists, delete it.

        if (usersTemplateRootFolder.toFile().exists())
        {
            System.out.println("Cleaning out existing test folder:" + usersTemplateRootFolder.toAbsolutePath());

            var templateFolderToBeDeleted = usersTemplateRootFolder.getParent();
            deleteDirectory(templateFolderToBeDeleted.toFile());

        }
    }

    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    @Nested
    @Order(1)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class WhenBasicOptions
    {
        @Test
        @Order(11)
        void canRunCommandlineHelp()
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));

            try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
                String[] args = new String[]{"-h"};
                PicocliRunner.run(HEPHApp.class, ctx, args);

                // Displays Help

                assertTrue(baos.toString()
                               .contains("template"));
            }
        }
    }

    @Nested
    @Order(2)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class WhenTemplate
    {
        @Test
        @Order(21)
        public void initCommandCanCreateUsersCabinet()
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));

            try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
                String[] args = new String[] {"template","init"};
                PicocliRunner.run(HEPHApp.class, ctx, args);

                assertTrue(Paths.get(StandardSystemProperty.USER_HOME.value(), ".heph", "test", "templates").toFile().exists(),"Users Cabinet");
            }
        }

        @Test
        @Order(22)
        public void addShelfCommandCanCreateShelfInUsersCabinet()
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));

            try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
                String[] args = new String[] {"template","add-shelf","shelf1"};
                PicocliRunner.run(HEPHApp.class, ctx, args);

                assertTrue(Paths.get(StandardSystemProperty.USER_HOME.value(), ".heph", "test", "templates","shelf1").toFile().exists(),"Shelf in users cabinet");
            }

            var str = baos.toString();
            assertTrue(str.contains("Created"));
            assertTrue(str.contains("templates/shelf1"));
        }

        @Test
        @Order(23)
        public void listShelvesCommandCanShowCreatedShelfInUsersCabinet()
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));

            try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
                String[] args = new String[] {"template","ls-shelf"};
                PicocliRunner.run(HEPHApp.class, ctx, args);
            }

            var str = baos.toString();
            assertTrue(str.contains("shelf1"));
        }
   }

    @Nested
    @Order(3)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class WhenGenerate
    {
        @Test
        @Order(31)
        public void generateCommandFromAShelfWorks()
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));

            try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
                String[] args = new String[] {"gen","--shelf","shelf1",
                        "--source","./src/test/resources/templates/",
                        "--target","./target/sample",
                        "--config","./src/test/resources/templates/shelf1/shelf1.toml",
                        "--real"};

                PicocliRunner.run(HEPHApp.class, ctx, args);
            }

            // Check the end results in the file system

            assertTrue(Paths.get(".","target","sample").toFile().exists(),"Target");
        }

    }

}
