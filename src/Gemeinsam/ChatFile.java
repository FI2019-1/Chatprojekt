package Gemeinsam;

import Gemeinsam.Benutzer;
import Gemeinsam.Nachricht;

import java.io.File;
import java.lang.reflect.Type;

public class ChatFile extends Nachricht {
    private File file;
    private Benutzer benutzer;

    public ChatFile() {

    }

    public ChatFile(File file, Benutzer benutzer) {
        this.file = file;
        this.benutzer = benutzer;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }

    @Override
    public String toString() {
        return file.getName();
    }

    @Override
    public Type getType() {
        return this.getClass();
    }
}
