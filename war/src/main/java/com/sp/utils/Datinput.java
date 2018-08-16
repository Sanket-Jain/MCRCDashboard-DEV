package com.sp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Datinput {
	public static void main(String args[]) {
        int n;
        ArrayList<String> al = new ArrayList<String>();
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        String da[] = new String[n];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        Date date[] = new Date[n];
        in.nextLine();
        for (int i = 0; i < da.length; i++) {
            da[i] = in.nextLine();
        }
        for (int i = 0; i < da.length; i++) {

            try {
                date[i] = sdf.parse(da[i]);
            } catch (ParseException e) {

                e.printStackTrace();
            }
        }

        in.close();
    }
}
