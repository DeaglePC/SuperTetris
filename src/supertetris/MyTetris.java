/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supertetris;

import java.util.ArrayList;

/**
 *
 * @author eagle
 */
public class MyTetris {

    /**
     *
     * @param MapWidth 地图宽度为多少个小方格
     * @param MapHeight 地图高度为多少个小方格
     */
    public MyTetris(int MapWidth, int MapHeight) {
        this.MapWidth = MapWidth;
        this.MapHeight = MapHeight;
        this.Score = 0;
        this.IsRun = false;
        this.IsFull = false;

        this.NowBlockOffset = new ArrayList<>();
        this.TertrisMap = new int[MapHeight][MapWidth];
    }

    public static class Position {

        int R, C;

        public Position(int R, int C) {
            this.R = R;
            this.C = C;
        }

        public int getR() {
            return R;
        }

        public int getC() {
            return C;
        }

        public void setR(int R) {
            this.R = R;
        }

        public void setC(int C) {
            this.C = C;
        }
    }

    private final int MapWidth;        // 地图的宽度是多少个小方格
    private final int MapHeight;       // 地图的高度是多少个小方格
    private int[][] TertrisMap;        // 地图矩阵，0表示没有方块，非零表示颜色为 int 的方块
    private boolean IsFull;            // 地图是否满了，满了则游戏结束
    private boolean IsRun;             // 游戏是否已经开始
    private MyBlock NowBlock;          // 现在的方块
    private MyBlock NextBlock;         // 下一个方块
    private int NowBlockPosR, NowBlockPosC;  // 现在方块的位置，r表示行，c表示列
    private ArrayList<Position> NowBlockOffset;   // 现在方块中所有小方块的坐标,相对于左下角的绝对坐标
    private int Score;                 // 当前得分

    private String FormatIntToBinary(int Val) {
        String res = Integer.toBinaryString(Val);
        String tmp = "";
        for (int i = 0; i < 16 - res.length(); i++) {
            tmp += "0";
        }
        return tmp + res;
    }

    private void updateBolckSet() {
        // 清空
        NowBlockOffset.clear();

        // 获取此方块的形状
        int value = NowBlock.getValue();
        String valString = FormatIntToBinary(value);
        for (int i = 0; i < 16; i++) {
            if (valString.charAt(i) == '1') {
                int row = 3 - i / 4;
                int col = i % 4;
                NowBlockOffset.add(new Position(row, col));
            }
        }
    }

    private void AddNewBlock() {
        if (IsFull) {
            return;
        }

        if (NowBlock == null) {
            NowBlock = new MyBlock();
            NextBlock = new MyBlock();
        } else {
            NowBlock = NextBlock;
            NextBlock = new MyBlock();
        }

        // 截去上方空白部分
        switch (NowBlock.getType()) {
            case I:
                NowBlockPosR = this.MapHeight - 4;
                break;
            case J:
            case L:
                NowBlockPosR = this.MapHeight - 3;
                break;
            default:
                NowBlockPosR = this.MapHeight - 2;
                break;
        }
        NowBlockPosC = (this.MapWidth - 4) / 2 + 1;
        updateBolckSet();

        for (int i = 0; i < NowBlockOffset.size(); i++) {
            int row = NowBlockOffset.get(i).getR();
            int col = NowBlockOffset.get(i).getC();
            int posR = NowBlockPosR + row;
            int posC = NowBlockPosC + col;

            // 如果放不下这个方块，就结束游戏
            if (posR >= MapHeight || posC >= MapWidth) {
                this.IsFull = true;
                break;
            }

            // 更新进地图
            if (this.TertrisMap[posR][posC] == 0) {
                this.TertrisMap[posR][posC] = NowBlock.getBlockColor();
            } else {
                this.IsFull = true;
                break;
            }
        }
    }

    /*
    * 清除一行
     */
    private void CleanRow(int row) {
        if (row >= this.MapHeight) {
            return;
        }

        for (int i = row; i < this.MapHeight - 1; i++) {
            System.arraycopy(this.TertrisMap[i + 1], 0, this.TertrisMap[i], 0, this.MapWidth);
        }
        for (int i = 0; i < this.MapWidth; i++) {
            this.TertrisMap[this.MapHeight - 1][i] = 0;
        }
    }

    /*
    * 检查第 row 行是否满了，满了就可以删除
    * @param row 检测第 row 行
     */
    private boolean CheckRow(int row) {
        for (int i = 0; i < this.MapWidth; i++) {
            if (this.TertrisMap[row][i] == 0) {
                return false;
            }
        }
        return true;
    }

    /*
    * 检测有木有需要消去的行，或者地图满了没有
     */
    private void Check() {
        int cleanNum = 0;
        // 先检查有木有可以消的行
        for (int i = 0; i < this.MapHeight; i++) {
            while (CheckRow(i)) {
                CleanRow(i);
                cleanNum++;
            }
        }
        if (cleanNum == 1) {
            this.Score += 100;
        } else if (cleanNum == 2) {
            this.Score += 200;
        } else if (cleanNum == 3) {
            this.Score += 400;
        } else if (cleanNum == 4) {
            this.Score += 800;
        }

        // 检测是否满了
        boolean flag = false;
        int num = 0;
        for (int i = 0; i < MapHeight; i++) {
            flag = false;
            for (int j = 0; j < MapWidth; j++) {
                if (TertrisMap[i][j] != 0) {
                    num++;
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                break;
            }
        }
        if (num == MapHeight - 1) {
            IsFull = true;
        }
    }

    /**
     *
     * @return 返回地图矩阵
     */
    public int[][] getMapValue() {
        return this.TertrisMap;
    }

    /**
     *
     * @return 当前方块，类型是方块类
     */
    public MyBlock getNowBlock() {
        return NowBlock;
    }

    /**
     *
     * @return 下一个方块，类型是方块类
     */
    public MyBlock getNextBlock() {
        return NextBlock;
    }
    
    /**
     * 获取下个方块的相对偏移量
     * @return 下个方块的相对偏移量，坐标型
     */
    public ArrayList<Position> getNextBolckValue(){
        int value = NextBlock.getValue();
        String valString = FormatIntToBinary(value);
        ArrayList<Position> resTmpArrayList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            if (valString.charAt(i) == '1') {
                int row = 3 - i / 4;
                int col = i % 4;
                resTmpArrayList.add(new Position(row, col));
            }
        }
        return resTmpArrayList;
    }

    /**
     *
     * @return 返回得分
     */
    public int getScore() {
        return this.Score;
    }

    /**
     * 开始运行游戏
     */
    public void runGame() {
        if (this.IsRun) {
            return;
        }

        this.IsRun = true;
        AddNewBlock();
    }

    /**
     *
     * @return 判断是否游戏已经结束
     */
    public boolean isGameOver() {
        return this.IsFull;
    }

    /**
     * 旋转当前下降的方块
     */
    public void Rotate() {
        if (IsFull) {
            return;
        }

        if (IsRun) {

            // 获取此方块的形状
            ArrayList<Position> nextOffset = new ArrayList<>();
            int value = NowBlock.getNextValue();
            String valString = FormatIntToBinary(value);
            for (int i = 0; i < 16; i++) {
                if (valString.charAt(i) == '1') {
                    int row = 3 - i / 4;
                    int col = i % 4;
                    nextOffset.add(new Position(row, col));
                }
            }

            for (Position tmpOffset : nextOffset) {
                int posR = NowBlockPosR + tmpOffset.getR();
                int posC = NowBlockPosC + tmpOffset.getC();

                // 如果超过了边界，不旋转
                if (posR < 0 || posC < 0 || posR >= this.MapHeight || posC >= this.MapWidth) {
                    return;
                }
                // 如果旋转后会和当前地图方块重叠，不旋转
                if (this.TertrisMap[posR][posC] != 0 && !isBlockSelf(posR, posC)) {
                    return;
                }
            }

            // 删除原来的
            for (Position tmpOffset : NowBlockOffset) {
                int posR = NowBlockPosR + tmpOffset.getR();
                int posC = NowBlockPosC + tmpOffset.getC();
                this.TertrisMap[posR][posC] = 0;
            }

            this.NowBlock.rotateBlock();
            updateBolckSet();

            for (Position tmpOffset : NowBlockOffset) {
                int posR = NowBlockPosR + tmpOffset.getR();
                int posC = NowBlockPosC + tmpOffset.getC();
                this.TertrisMap[posR][posC] = NowBlock.getBlockColor();
            }
        }
    }

    /**
     * 移动的方向
     */
    public static enum Direction {

        /**
         * 下
         */
        DOWN,
        /**
         * 左
         */
        LEFT,
        /**
         * 右
         */
        RIGHT
    }

    /*
    * 判断这个坐标是不是当前下落方块其中的小方块
     */
    private boolean isBlockSelf(int r, int c) {
        for (int i = 0; i < NowBlockOffset.size(); i++) {
            int row = NowBlockOffset.get(i).getR();
            int col = NowBlockOffset.get(i).getC();
            int posR = NowBlockPosR + row;
            int posC = NowBlockPosC + col;

            if (posR == r && posC == c) {
                return true;
            }
        }
        return false;
    }

    /*
    * 检测碰撞
    * @ return 能否向某个方向移动一格
     */
    private boolean CanMove(Direction direction) {
        for (int i = 0; i < NowBlockOffset.size(); i++) {
            int row = NowBlockOffset.get(i).getR();
            int col = NowBlockOffset.get(i).getC();
            int posR = NowBlockPosR + row;
            int posC = NowBlockPosC + col;

            if (null != direction) {
                switch (direction) {
                    case DOWN:
                        // 如果已经到底了，直接返回否
                        if (posR == 0) {
                            Check();    // 检测清除等
                            this.AddNewBlock();
                            this.Score += 10;
                            return false;
                        }   // 检测下方是否有方块
                        if (this.TertrisMap[posR - 1][posC] != 0
                                && !isBlockSelf(posR - 1, posC)) {
                            Check();    // 检测清除等
                            this.Score += 10;
                            this.AddNewBlock();
                            return false;
                        }
                        break;
                    case LEFT:
                        // 如果到最左边了
                        if (posC == 0) {
                            return false;
                        }   // 检测左边是否有方块
                        if (this.TertrisMap[posR][posC - 1] != 0
                                && !isBlockSelf(posR, posC - 1)) {
                            return false;
                        }
                        break;
                    case RIGHT:
                        // 如果到最右边了
                        if (posC == this.MapWidth - 1) {
                            return false;
                        }   // 检测右边是否有方块
                        if (this.TertrisMap[posR][posC + 1] != 0
                                && !isBlockSelf(posR, posC + 1)) {
                            return false;
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        return true;
    }

    /**
     * 把当前方块向某方向移动一格
     *
     * @param direction 要移动的方向
     */
    public void Move(Direction direction) {
        if (IsFull) {
            return;
        }

        if (IsRun) {
            if (CanMove(direction)) {
                int PreBlockPosR = this.NowBlockPosR;
                int PreBlockPosC = this.NowBlockPosC;

                // 判断方向
                switch (direction) {
                    case DOWN:
                        this.NowBlockPosR--;
                        break;
                    case LEFT:
                        this.NowBlockPosC--;
                        break;
                    case RIGHT:
                        this.NowBlockPosC++;
                        break;
                    default:
                        break;
                }

                // 消去原来的
                for (int i = 0; i < NowBlockOffset.size(); i++) {
                    int row = NowBlockOffset.get(i).getR();
                    int col = NowBlockOffset.get(i).getC();
                    int preR = PreBlockPosR + row;
                    int preC = PreBlockPosC + col;

                    this.TertrisMap[preR][preC] = 0;
                }

                // 填充现在的
                for (int i = 0; i < NowBlockOffset.size(); i++) {
                    int row = NowBlockOffset.get(i).getR();
                    int col = NowBlockOffset.get(i).getC();
                    int posR = NowBlockPosR + row;
                    int posC = NowBlockPosC + col;

                    this.TertrisMap[posR][posC] = NowBlock.getBlockColor();
                }
            }
        }
    }

    /**
     *
     * @return 当前下落的方块在地图中的最终位置的点集
     */
    public ArrayList<Position> lastPosition() {
        ArrayList<Position> res = new ArrayList<>();

        int num = 0;
        boolean flag = false;
        while (true) {
            for (int i = 0; i < NowBlockOffset.size(); i++) {
                int row = NowBlockOffset.get(i).getR();
                int col = NowBlockOffset.get(i).getC();
                int posR = NowBlockPosR + row - num;
                int posC = NowBlockPosC + col;

                if (posR == 0) {
                    flag = true;
                    break;
                }

                if (this.TertrisMap[posR - 1][posC] != 0
                        && !isBlockSelf(posR - 1, posC)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
            num++;
        }

        for (int i = 0; i < NowBlockOffset.size(); i++) {
            int row = NowBlockOffset.get(i).getR();
            int col = NowBlockOffset.get(i).getC();
            int posR = NowBlockPosR + row - num;
            int posC = NowBlockPosC + col;
            res.add(new Position(posR, posC));
        }
        return res;
    }

    /**
     * 把当前方块移动到最底部（一直向下移动，不能移动了为止）
     */
    public void goBottom() {
        if (IsFull) {
            return;
        }

        if (IsRun) {
            // 消去原来的
            for (int i = 0; i < NowBlockOffset.size(); i++) {
                int row = NowBlockOffset.get(i).getR();
                int col = NowBlockOffset.get(i).getC();
                int preR = NowBlockPosR + row;
                int preC = NowBlockPosC + col;

                this.TertrisMap[preR][preC] = 0;
            }

            ArrayList<Position> last = lastPosition();

            // 填充现在的
            for (Position pos : last) {
                int posR = pos.getR();
                int posC = pos.getC();
                this.TertrisMap[posR][posC] = this.NowBlock.getBlockColor();
            }
            Check();            // 检测清除
            this.AddNewBlock(); // 添加新方块
            this.Score += 10;
        }
    }
}
