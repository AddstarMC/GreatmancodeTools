package com.greatmancode.tools.configuration.sponge;

import com.greatmancode.tools.configuration.Config;
import com.greatmancode.tools.interfaces.SpongeLoader;
import com.greatmancode.tools.interfaces.caller.ServerCaller;
import com.typesafe.config.ConfigValueFactory;
import org.spongepowered.api.util.config.ConfigFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class SpongeConfig extends Config {

    private com.typesafe.config.Config file;
    public SpongeConfig(InputStream is, ServerCaller serverCaller) {
        super(is, serverCaller);
        //No can't do.
    }

    public SpongeConfig(File folder, String fileName, ServerCaller serverCaller) {
        super(folder, fileName, serverCaller);
        file = (com.typesafe.config.Config) ConfigFile.parseFile(new File(folder, fileName));
    }

    @Override
    public int getInt(String path) {
        return file.getInt(path);
    }

    @Override
    public long getLong(String path) {
        return file.getLong(path);
    }

    @Override
    public double getDouble(String path) {
        return file.getDouble(path);
    }

    @Override
    public String getString(String path) {
        return file.getString(path);
    }

    @Override
    public boolean getBoolean(String path) {
        return file.getBoolean(path);
    }

    @Override
    public void setValue(String path, Object value) {
        file = file.withValue(path, ConfigValueFactory.fromAnyRef(value));
        //TODO: Write
    }

    @Override
    public boolean has(String path) {
        return file.hasPath(path)
    }

    @Override
    public Map<String, String> getStringMap(String path) {
        return null;
    }

    @Override
    public List<String> getStringList(String path) {
        return file.getStringList(path);
    }
}