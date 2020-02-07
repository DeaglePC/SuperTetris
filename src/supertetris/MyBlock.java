/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supertetris;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author eagle
 */
public class MyBlock {

    /**
     * 构造函数，初始化并随机生个一个方块
     */
    public MyBlock() {
        initList();

        Random randm = new Random();
        int rmInt = randm.nextInt(7);
        this.Type = MapShape(rmInt);
        this.RotateState = 0;
        this.BlockColor = randm.nextInt(8) + 1;
    }

    /**
     * 方块的类型标识
     */
    public static enum BlockShape {

        /**
         * 形状为 I 的方块
         */
        I,
        /**
         * 形状为 J 的方块
         */
        J,
        /**
         * 形状为 L 的方块
         */
        L,
        /**
         * 形状为 O 的方块
         */
        O,
        /**
         * 形状为 S 的方块
         */
        S,
        /**
         * 形状为 Z 的方块
         */
        Z,
        /**
         * 形状为 T 的方块
         */
        T
    }

    /*
    * 方块的颜色，1-8
     */
    private final int BlockColor;

    /**
     *
     * @return 块颜色
     */
    public int getBlockColor() {
        return BlockColor;
    }

    /*
    * 方块当前是哪种
     */
    private final BlockShape Type;

    private int RotateState;

    /**
     * 保存每一种方块的4种旋转状态
     */
    protected ArrayList<Integer> BlockLists_I;
    protected ArrayList<Integer> BlockLists_J;
    protected ArrayList<Integer> BlockLists_L;
    protected ArrayList<Integer> BlockLists_O;
    protected ArrayList<Integer> BlockLists_S;
    protected ArrayList<Integer> BlockLists_T;
    protected ArrayList<Integer> BlockLists_Z;

    private BlockShape MapShape(int num) {
        switch (num) {
            case 0:
                return BlockShape.I;
            case 1:
                return BlockShape.J;
            case 2:
                return BlockShape.L;
            case 3:
                return BlockShape.O;
            case 4:
                return BlockShape.S;
            case 5:
                return BlockShape.T;
            case 6:
                return BlockShape.Z;
            default:
                return null;
        }
    }

    private void initList() {
        BlockLists_I = new ArrayList<>();
        BlockLists_J = new ArrayList<>();
        BlockLists_L = new ArrayList<>();
        BlockLists_O = new ArrayList<>();
        BlockLists_S = new ArrayList<>();
        BlockLists_T = new ArrayList<>();
        BlockLists_Z = new ArrayList<>();

        BlockLists_I.add(0x4444);
        BlockLists_I.add(0x000f);
        BlockLists_I.add(0x4444);
        BlockLists_I.add(0x000f);

        BlockLists_J.add(0x0226);
        BlockLists_J.add(0x0047);
        BlockLists_J.add(0x0644);
        BlockLists_J.add(0x00e2);

        BlockLists_L.add(0x0446);
        BlockLists_L.add(0x0074);
        BlockLists_L.add(0x0622);
        BlockLists_L.add(0x002e);

        BlockLists_O.add(0x0066);
        BlockLists_O.add(0x0066);
        BlockLists_O.add(0x0066);
        BlockLists_O.add(0x0066);

        BlockLists_S.add(0x006c);
        BlockLists_S.add(0x0462);
        BlockLists_S.add(0x006c);
        BlockLists_S.add(0x0462);

        BlockLists_T.add(0x00e4);
        BlockLists_T.add(0x04c4);
        BlockLists_T.add(0x004e);
        BlockLists_T.add(0x0464);

        BlockLists_Z.add(0x00c6);
        BlockLists_Z.add(0x0264);
        BlockLists_Z.add(0x00c6);
        BlockLists_Z.add(0x0264);
    }

    /**
     *
     * @return 方块类型
     */
    public BlockShape getType() {
        return Type;
    }

    public void rotateBlock() {
        this.RotateState = (this.RotateState + 1) % 4;
    }

    /**
     *
     * @return 方块的详细参数
     */
    public Integer getValue() {
        if (null == Type) {
            return null;
        } else {
            switch (Type) {
                case I:
                    return BlockLists_I.get(RotateState);
                case J:
                    return BlockLists_J.get(RotateState);
                case L:
                    return BlockLists_L.get(RotateState);
                case O:
                    return BlockLists_O.get(RotateState);
                case S:
                    return BlockLists_S.get(RotateState);
                case T:
                    return BlockLists_T.get(RotateState);
                default:
                    return BlockLists_Z.get(RotateState);
            }
        }
    }

    public Integer getNextValue() {
        if (null == Type) {
            return null;
        } else {
            int tmp = (RotateState + 1) % 4;
            switch (Type) {
                case I:
                    return BlockLists_I.get(tmp);
                case J:
                    return BlockLists_J.get(tmp);
                case L:
                    return BlockLists_L.get(tmp);
                case O:
                    return BlockLists_O.get(tmp);
                case S:
                    return BlockLists_S.get(tmp);
                case T:
                    return BlockLists_T.get(tmp);
                default:
                    return BlockLists_Z.get(tmp);
            }
        }
    }
}
