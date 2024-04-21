package org.saltations.hephaestus;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.conversion.ObjectConverter;
import com.electronwill.nightconfig.core.conversion.Path;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.electronwill.nightconfig.toml.TomlWriter;
import io.micronaut.serde.config.annotation.SerdeConfig;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class HEPHConfigTOMLTest
{
    @Test
    void loadConfigAndPrintOut()
    {
        var configFile = new File("src/test/resources/config/sample-heph-config.toml");
        var config = FileConfig.of(configFile);
        config.load();

        config.entrySet().forEach(entry -> System.out.println(entry.getKey() + " : " + entry.getValue().toString().replace("SimpleCommentedConfig:","")));

        var converter = new ObjectConverter();
        var hephConfig = converter.toObject(config, OverallConfig::new);

        System.out.println(hephConfig.getHeph().getVersion());
    }

    @Test
    void createAndDump()
    {
        var cfg = Config.inMemory();

        cfg.set("heph.version", "1.0.0");
        cfg.set("heph.description", "Sample HEPH Config");

        cfg.set("cabinet.cab1.name", "Cabinet 1");
        cfg.set("cabinet.cab1.description", "First Cabinet");
        cfg.set("cabinet.cab1.path", "file:///tmp/cabinet1");

        cfg.set("cabinet.cab1.shelf.shelf1.name", "Shelf1");
        cfg.set("cabinet.cab1.shelf.shelf1.description", "First Shelf");
        cfg.set("cabinet.cab1.shelf.shelf1.path", "shelf1");

        cfg.set("cabinet.cab1.shelf.shelf1.entry.java-dir-1.name", "JavaCRUDProject ");
        cfg.set("cabinet.cab1.shelf.shelf1.entry.java-dir-1.description", "Java CRUD Project Directory");
        cfg.set("cabinet.cab1.shelf.shelf1.entry.java-dir-1.path", "crud-project");
        cfg.set("cabinet.cab1.shelf.shelf1.entry.java-dir-1.type", "directory");

        cfg.set("cabinet.cab1.shelf.shelf1.entry.java-file-1.name", "JavaEntity");
        cfg.set("cabinet.cab1.shelf.shelf1.entry.java-file-1.description", "Java Entity File");
        cfg.set("cabinet.cab1.shelf.shelf1.entry.java-file-1.path", "Entity.java");
        cfg.set("cabinet.cab1.shelf.shelf1.entry.java-file-1.type", "file");

        cfg.set("cabinet.cab1.shelf.shelf2.name", "Shelf2");
        cfg.set("cabinet.cab1.shelf.shelf2.description", "Second Shelf");
        cfg.set("cabinet.cab1.shelf.shelf2.path", "shelf2");

        cfg.set("cabinet.cab2.name", "Cabinet 2");
        cfg.set("cabinet.cab2.description", "Second Cabinet");
        cfg.set("cabinet.cab2.path", "file:///tmp/cabinet2");

        cfg.set("cabinet.cab2.shelf.shelf1.name", "Shelf1");
        cfg.set("cabinet.cab2.shelf.shelf1.description", "First Shelf");
        cfg.set("cabinet.cab2.shelf.shelf1.path", "shelf1");

        cfg.set("cabinet.cab2.shelf.shelf1.entry.java-dir-1.name", "JavaCRUDProject ");
        cfg.set("cabinet.cab2.shelf.shelf1.entry.java-dir-1.description", "Java CRUD Project Directory");
        cfg.set("cabinet.cab2.shelf.shelf1.entry.java-dir-1.path", "crud-project");
        cfg.set("cabinet.cab2.shelf.shelf1.entry.java-dir-1.type", "directory");

        cfg.set("cabinet.cab2.shelf.shelf1.entry.java-file-1.name", "JavaEntity");
        cfg.set("cabinet.cab2.shelf.shelf1.entry.java-file-1.description", "Java Entity File");
        cfg.set("cabinet.cab2.shelf.shelf1.entry.java-file-1.path", "Entity.java");
        cfg.set("cabinet.cab2.shelf.shelf1.entry.java-file-1.type", "file");

        cfg.set("cabinet.cab2.shelf.shelf2.name", "Shelf2");
        cfg.set("cabinet.cab2.shelf.shelf2.description", "Second Shelf");
        cfg.set("cabinet.cab2.shelf.shelf2.path", "shelf2");

        cfg.remove("cabinet.cab2.shelf.shelf1");

        var configWriter = new TomlWriter();
        var output = configWriter.writeToString(cfg);

        System.out.println(output);

    }


    static class OverallConfig
    {
        HEPHConfig heph;

        public HEPHConfig getHeph()
        {
            return heph;
        }

        public void setHeph(HEPHConfig heph)
        {
            this.heph = heph;
        }
    }

    static class HEPHConfig
    {
        private String version;
        private String description;

        @Path("cabinet")
        private List<CabinetConfig> cabinets;

        public String getVersion()
        {
            return version;
        }

        public void setVersion(String version)
        {
            this.version = version;
        }

        public String getDescription()
        {
            return description;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }
    }

    static class CabinetConfig
    {
        private String description;
        private URL path;

        public String getDescription()
        {
            return description;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }

        public URL getPath()
        {
            return path;
        }

        public void setPath(URL path)
        {
            this.path = path;
        }
    }

}

