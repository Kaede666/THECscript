/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thec.kapi.util;

/**
 *
 * @author William
 */
public class Arrays extends com.thec.api.util.Arrays{
    public static int[] merge(final int[] ...arrays ) {
        int size = 0;
        for (int[] a: arrays) size += a.length;
        int[] a = new int[size];
        int destPos = 0;
        for (int i = 0; i < arrays.length; i++) {
            System.arraycopy(arrays[i], 0, a, destPos, arrays[i].length);
            destPos += arrays[i].length;
        }
        return a;
    }   
}
