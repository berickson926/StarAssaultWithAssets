package net.obviam.starassault.utils;

import com.badlogic.gdx.Gdx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Decompress
{
    private String _zipFile;
    private String _location;

    public Decompress(String zipFile, String location)
    {
        _zipFile = zipFile;
        _location = location;

        _dirChecker("");
    }

    public void unzip()
    {
        try
        {
            FileInputStream fin = new FileInputStream(_zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                Gdx.app.log("Decompress", "Unzipping " + ze.getName());

                if(ze.isDirectory())
                {
                    _dirChecker(ze.getName());
                }
                else
                {
                    Gdx.app.log("Decompress", "Fileoutput: "+_location+ze.getName());
                    FileOutputStream fout = new FileOutputStream(_location + ze.getName());
                    for (int c = zin.read(); c != -1; c = zin.read())
                    {
                        fout.write(c);
                    }

                    zin.closeEntry();
                    fout.close();
                }

            }
            zin.close();
        }
        catch(Exception e)
        {
            Gdx.app.setLogLevel(5);
            Gdx.app.log("Decompress", "unzip", e);
        }

    }

    private void _dirChecker(String dir)
    {
        File f = new File(_location + dir);

        if(!f.isDirectory())
        {
            f.mkdirs();
        }
    }
}
