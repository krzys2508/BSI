package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;


import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;

public class Logic extends JFrame
{
    /**
     * Application that encrypts and decrypts files.
     * Application works in window mode so it is more user friendly
     */

    enum RodzajSzyfrowania
    {
        AES("AES", "aes", "moj klucz do AES"),
        DES("DES","des", "moj klucz do DES"),
        TRIPLEDES("TRIPLEDES","3des","moj klucz do 3des"),
        UNKNOWN("UNKNOWN","");

        private String name;
        private String key;
        private String extension;

        /**
         * Here we allow selection of the unknown cypher later
         * @param pName = name of the cypher
         * @param pExt = extension that will be added to the file
         */
        RodzajSzyfrowania(String pName, String pExt)
        {
            name = pName;
            extension = pExt;
        }

        /**
         *  Here we allow selection of the known cypher values that can be used later
         * @param pName = Name of the cypher
         * @param pExt = Extension of the cypher
         * @param pKey = Secret kley used in Encryption and Decryption tool
         */
        RodzajSzyfrowania(String pName, String pExt, String pKey)
        {
            name = pName;
            extension = pExt;
            key = pKey;
        }

        /**
         *
         * @return = return the name of the cypher
         */
        String getName()
        {
            return name;
        }

        /**
         *
         * @return = return the key of the cypher
         */

        String getKey()
        {
            return key;
        }

        /**
         *
         * @return = return extension of the cypher
         */
        String getExt()
        {
            return extension;
        }
    }

    /**
     * Setting up labels later used in the window
     */
    private RodzajSzyfrowania rodzajSzyfr = RodzajSzyfrowania.AES;

    String podajNazwePliku = "Podaj nazwe pliku to szyfrowania za pomocą ";
    private JLabel labelDes = new JLabel(podajNazwePliku + RodzajSzyfrowania.DES.getName());
    private JLabel labelThreeDes = new JLabel(podajNazwePliku + RodzajSzyfrowania.TRIPLEDES.getName());
    private JLabel labelAes = new JLabel(podajNazwePliku + RodzajSzyfrowania.AES.getName());

    private JLabel labelNieznaneSzyfrowanie = new JLabel("Nie mozna rozszyfrowac plik o podanym rozszerzeniu !");
    private JLabel labelPlikZaszyfrowano = new JLabel("Plik zostal zaszyfrowany.");
    private JLabel labelPlikOdszyfrowano = new JLabel("Plik zostal odszyfrowany.");

    /**
     *
     * @param title = name of the app
     * @throws HeadlessException
     */

    public Logic(String title) throws HeadlessException
    {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(null);

        przygotujEkran();

        dodajMenuSzyfruj();
    }

    /**
     * Prepping the screen visually
     */

    private void przygotujEkran()
    {
        labelAes.setBounds(10, 10, 400, 30);
        add(labelAes);
        JTextField plik = new JTextField();
        plik.setBounds(10, 50, 400, 30);
        add(plik);
        JButton deszyfruj = new JButton("Deszyfruj");
        deszyfruj.setBounds(130, 120, 100, 30);
        add(deszyfruj);
        JButton szyfruj = new JButton("Szyfruj");
        szyfruj.setBounds(40, 120, 100, 30);
        add(szyfruj);
        szyfruj.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                remove(labelPlikOdszyfrowano);
                remove(labelNieznaneSzyfrowanie);
                repaint(); // odwiezanie okna
                try {
                    File inputFile = new File(plik.getText());
                    File outputFile = new File( inputFile.getPath() + "." + getRodzajSzyfr().getExt());
                    szyfruj(inputFile, outputFile);
                    labelPlikZaszyfrowano.setBounds(10, 80, 400, 30);
                    add(labelPlikZaszyfrowano);
                    repaint();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                    System.out.println("Zła nazwa");
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (NoSuchPaddingException ex) {
                    ex.printStackTrace();
                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                } catch (InvalidKeyException ex) {
                    ex.printStackTrace();
                } catch (IllegalBlockSizeException ex) {
                    ex.printStackTrace();
                } catch (BadPaddingException ex) {
                    ex.printStackTrace();
                }
            }
        });
        deszyfruj.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                remove(labelPlikZaszyfrowano);
                remove(labelNieznaneSzyfrowanie);
                repaint();
                try {
                    RodzajSzyfrowania rodzajSzyfrowaniaPliku = jakieSzyfrowanie(plik.getText()); //
                    if (!rodzajSzyfrowaniaPliku.getName().equals(RodzajSzyfrowania.UNKNOWN.getName())) {
                        File plikZaszyfrowany = new File(plik.getText());
                        File plikOdszyfrowany = new File(dajNazwePlikuOdszyfrowanego(plik.getText()));
                        deszyfruj(rodzajSzyfrowaniaPliku, plikZaszyfrowany, plikOdszyfrowany);
                        labelPlikOdszyfrowano.setBounds(10, 80, 400, 30);
                        add(labelPlikOdszyfrowano);
                        repaint();
                    } else {
                        System.out.println("Nie mozna rozszyfrowac plik o podanym rozszerzeniu !");
                        labelNieznaneSzyfrowanie.setBounds(10, 80, 400, 30);
                        add(labelNieznaneSzyfrowanie);
                        repaint();
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                    System.out.println("Zła nazwa");
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (NoSuchPaddingException ex) {
                    ex.printStackTrace();
                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                } catch (InvalidKeyException ex) {
                    ex.printStackTrace();
                } catch (IllegalBlockSizeException ex) {
                    ex.printStackTrace();
                } catch (BadPaddingException ex) {
                    ex.printStackTrace();
                }
            }
        });
        repaint();
    }

    /**
     *
     * @param nazwaZaszyfrowanegoPliku = name of the encrypted file
     * @return = returns the decrypted file with the new name
     */


    private String dajNazwePlikuOdszyfrowanego(String nazwaZaszyfrowanegoPliku)
    {
        String nazwaPliku ="Odszyfrowany " + rodzajSzyfr.extension +" "+ nazwaZaszyfrowanegoPliku.substring(0,nazwaZaszyfrowanegoPliku.length() - 4);
        return nazwaPliku;
    }

    /**
     *
     * @param nazwaZaszyfrowanegoPliku = name of the encrypted file
     * @return = type of the cypher
     */

    private RodzajSzyfrowania jakieSzyfrowanie(String nazwaZaszyfrowanegoPliku)
    {
        RodzajSzyfrowania jakiRodzajSzydfrowania = RodzajSzyfrowania.UNKNOWN;
        String rozszerzeniePliku = nazwaZaszyfrowanegoPliku.substring(nazwaZaszyfrowanegoPliku.length() - 3);
        if (rozszerzeniePliku.equalsIgnoreCase(RodzajSzyfrowania.TRIPLEDES.getExt())) {
            jakiRodzajSzydfrowania = RodzajSzyfrowania.TRIPLEDES;
        } else if (rozszerzeniePliku.equalsIgnoreCase(RodzajSzyfrowania.AES.getExt())) {
            jakiRodzajSzydfrowania = RodzajSzyfrowania.AES;
        } else if (rozszerzeniePliku.equalsIgnoreCase(RodzajSzyfrowania.DES.getExt())) {
            jakiRodzajSzydfrowania = RodzajSzyfrowania.DES;
        }
        return jakiRodzajSzydfrowania;
    }

    /**
     *
     * @return - type of the cypher
     */

    public RodzajSzyfrowania getRodzajSzyfr()
    {
        return rodzajSzyfr;
    }

    /**
     *  Setting the cypher type
     * @param rodzajSzyfr = type of the cypher
     */

    public void setRodzajSzyfr(RodzajSzyfrowania rodzajSzyfr)
    {
        this.rodzajSzyfr = rodzajSzyfr;
    }

    /**
     * Adding the visual drop down menu for different cyphers
     */
    private void dodajMenuSzyfruj()
    {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        JMenuBar menuBar = new JMenuBar();
        JMenu typSzyfrowania = new JMenu("Szyfr");
        /**
         * Case of TripleDES
         */
        JMenuItem tripleDes = new JMenuItem(new AbstractAction(RodzajSzyfrowania.TRIPLEDES.getName())
        {
            public void actionPerformed(ActionEvent e)
            {
                remove(labelAes);
                remove(labelDes);
                remove(labelNieznaneSzyfrowanie);
                remove(labelPlikZaszyfrowano);
                remove(labelPlikOdszyfrowano);
                labelThreeDes.setBounds(10, 10, 400, 30);
                add(labelThreeDes);
                repaint();
                setRodzajSzyfr(RodzajSzyfrowania.TRIPLEDES);
            }
        });
        typSzyfrowania.add(tripleDes);

        /**
         * Case of AES
         */
        JMenuItem aes = new JMenuItem(new AbstractAction(RodzajSzyfrowania.AES.getName())
        {
            public void actionPerformed(ActionEvent e)
            {
                remove(labelThreeDes);
                remove(labelDes);
                remove(labelNieznaneSzyfrowanie);
                remove(labelPlikZaszyfrowano);
                remove(labelPlikOdszyfrowano);
                labelAes.setBounds(10, 10, 400, 30);
                add(labelAes);
                repaint();
                setRodzajSzyfr(RodzajSzyfrowania.AES);
            }
        });
        typSzyfrowania.add(aes);

        /**
         * Case of DES
         */
        JMenuItem des = new JMenuItem(new AbstractAction(RodzajSzyfrowania.DES.getName())
        {
            public void actionPerformed(ActionEvent e)
            {
                remove(labelAes);
                remove(labelThreeDes);
                remove(labelNieznaneSzyfrowanie);
                remove(labelPlikZaszyfrowano);
                remove(labelPlikOdszyfrowano);
                labelDes.setBounds(10, 10, 400, 30);
                add(labelDes);
                repaint();
                setRodzajSzyfr(RodzajSzyfrowania.DES);
            }
        });
        typSzyfrowania.add(des);

        menuBar.add(typSzyfrowania);
        setJMenuBar(menuBar);
        setVisible(true);
    }

    /**
     * Encrypting function
     * @param inputFile - The file to be encrypted
     * @param outputFile - Encrypted file
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public void szyfruj(File inputFile, File outputFile) throws BadPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException
    {
        switch (getRodzajSzyfr()) {
            case TRIPLEDES:I:
                szyfruj3DES(inputFile, outputFile);
                break;
            case AES:
                szyfrujAES(inputFile, outputFile);
                break;
            case DES:
                szyfrujDES(inputFile, outputFile);
                break;
        }
    }

    /**
     *
     * @param inputFile = File to be encypted via AES
     * @param outputFile + File encrypted via AES
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    private void szyfrujAES(File inputFile, File outputFile) throws BadPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException
    {
        doCrypto(RodzajSzyfrowania.AES, Cipher.ENCRYPT_MODE, inputFile, outputFile);
    }

    /**
     *
     * @param inputFile = File to be encrypted via DES
     * @param outputFile = File Encrypted by DES
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */

    private void szyfrujDES(File inputFile, File outputFile) throws BadPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException
    {
        doCrypto(RodzajSzyfrowania.DES, Cipher.ENCRYPT_MODE, inputFile, outputFile);
    }

    /**
     *
     * @param inputFile = File to be encrypted via 3DES
     * @param outputFile = File encrypted via 3DES
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    private void szyfruj3DES (File inputFile, File outputFile)throws BadPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException {
        doCrypto(RodzajSzyfrowania.TRIPLEDES, Cipher.ENCRYPT_MODE, inputFile, outputFile);
    }

    /**
     *
     * @param rodzajSzyfr = Cypher type
     * @param inputFile = Encrypted file
     * @param outputFile = Decrypted file
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public void deszyfruj(RodzajSzyfrowania rodzajSzyfr, File inputFile, File outputFile) throws BadPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException
    {
        switch (rodzajSzyfr) {
            case TRIPLEDES:
                deszyfruj3DES(inputFile, outputFile);
                break;
            case AES:
                deszyfrujAES(inputFile, outputFile);
                break;
            case DES:
                deszyfrujDES( inputFile, outputFile);
                break;
        }
    }


    /**
     *
     * @param inputFile = Encrypted file via AES
     * @param outputFile = Decrypted file via AES
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public void deszyfrujAES(File inputFile, File outputFile) throws BadPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException
    {
        doCrypto(RodzajSzyfrowania.AES, Cipher.DECRYPT_MODE, inputFile, outputFile);
    }

    /**
     *
     * @param inputFile = Encrypted file via DES
     * @param outputFile = Decrypted file via DES
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */

    public void deszyfrujDES(File inputFile, File outputFile) throws BadPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException
    {
        doCrypto(RodzajSzyfrowania.DES, Cipher.DECRYPT_MODE, inputFile, outputFile);
    }

    /**
     *
     * @param inputFile = File encrypted via 3DES
     * @param outputFile = Decrypted file via 3DES
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public void deszyfruj3DES(File inputFile, File outputFile) throws BadPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException
    {
        doCrypto(RodzajSzyfrowania.TRIPLEDES, Cipher.DECRYPT_MODE, inputFile, outputFile);
    }


    /**
     * Function that either decrypts or encrypts the file
     * @param rodzajSzyfr = Cypher type
     * @param cipherMode = Decrypting or Encrypting
     * @param inputFile = File to be modified
     * @param outputFile = File modified
     * @throws IOException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     */
    private void doCrypto(RodzajSzyfrowania rodzajSzyfr, int cipherMode, File inputFile, File outputFile) throws IOException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException
    {
        Cipher cipher = Cipher.getInstance(rodzajSzyfr.getName());

        switch (rodzajSzyfr) {
            case AES:
                Key secretKey = new SecretKeySpec(rodzajSzyfr.getKey().getBytes(), rodzajSzyfr.getName());
                cipher.init(cipherMode, secretKey);
                break;
            case DES:
                try {
                    Key myDesKey = SecretKeyFactory.getInstance(rodzajSzyfr.getName()).generateSecret(new DESKeySpec(rodzajSzyfr.getKey().getBytes()));
                    cipher.init(cipherMode, myDesKey);
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }
                break;
            case TRIPLEDES:
                String keyCaptials = rodzajSzyfr.getKey().toUpperCase();
                    byte[] digestOfPassword = keyCaptials.getBytes();
                    byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
                    SecretKey key = new SecretKeySpec(keyBytes, "DESede");
                    cipher.init(cipherMode, key);

                    break;
                }

        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] inputBytes = new byte[(int) inputFile.length()];
        inputStream.read(inputBytes);

        byte[] outputBytes = cipher.doFinal(inputBytes);

        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(outputBytes);

        inputStream.close();
        outputStream.close();
    }
}