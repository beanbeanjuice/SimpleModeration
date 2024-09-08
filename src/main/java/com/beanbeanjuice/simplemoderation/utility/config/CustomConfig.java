package com.beanbeanjuice.simplemoderation.utility.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import com.beanbeanjuice.simplemoderation.utility.StringUtil;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
public class CustomConfig {

    private YamlDocument yamlConfig;
    private YamlDocument yamlMessages;
    private final File configFolder;
    private final HashMap<ConfigKey, Object> config = new HashMap<>();
    private final ArrayList<Runnable> reloadFunctions = new ArrayList<>();

    public void initialize() {
        try {
            yamlConfig = loadConfig("config.yml");
            yamlMessages = loadConfig("messages.yml");
            yamlConfig.update();
            yamlMessages.update();
            yamlConfig.save();
            yamlMessages.save();
            readConfig();
        } catch (IOException ignored) { }
    }

    public void addReloadListener(Runnable runnable) {
        reloadFunctions.add(runnable);
    }

    public void reload() {
        try {
            yamlConfig.reload();
            yamlMessages.reload();
            readConfig();
            reloadFunctions.forEach(Runnable::run);
        } catch (IOException ignored) { }
    }

    private Object get(final ConfigKey key) {
        return config.get(key);
    }

//    public String getAsString(final ConfigKey key) {
//        return (String) get(key);
//    }

    public <T> T get(final ConfigKey key, final Class<T> type) {
        Object value = get(key);
        return type.cast(value);
    }

//    public int getAsInteger(final ConfigKey key) {
//        return (int) get(key);
//    }
//
//    public boolean getAsBoolean(final ConfigKey key) {
//        return (boolean) get(key);
//    }
//
//    public Optional<Color> getAsColor(final ConfigKey key) {
//        try { return Optional.of(Color.decode(getAsString(key))); }
//        catch (NumberFormatException e) { return Optional.empty(); }
//    }
//
//    @SuppressWarnings("unchecked")
//    public HashMap<String, String> getAsStringMap(final ConfigKey key) {
//        return (HashMap<String, String>) get(key);
//    }
//
//    @SuppressWarnings("unchecked")
//    public ArrayList<String> getAsArrayList(final ConfigKey key) {
//        return (ArrayList<String>) get(key);
//    }

    private void readConfig() throws IOException {
        Arrays.stream(ConfigKey.values()).forEach(this::addToConfigMap);
    }

    private void addToConfigMap(final ConfigKey key) {
        Object value = (key.getType() == ConfigType.GENERAL) ? yamlConfig.get(key.getKey()) : yamlMessages.get(key.getKey());

        if (key.getClassType() == String.class) {
            value = StringUtil.replaceEssentialsColorCodes((String) value);
        }

        config.put(key, value);
    }

    private YamlDocument loadConfig(String fileName) throws IOException {
        return YamlDocument.create(
                new File(configFolder, fileName),
                Objects.requireNonNull(getClass().getResourceAsStream("/" + fileName)),
                GeneralSettings.DEFAULT,
                LoaderSettings.builder().setAutoUpdate(true).build(),
                DumperSettings.DEFAULT,
                UpdaterSettings.builder()
                        .setVersioning(new BasicVersioning("file-version"))
                        .setOptionSorting(UpdaterSettings.OptionSorting.SORT_BY_DEFAULTS)
                        .build()
        );
    }

}
