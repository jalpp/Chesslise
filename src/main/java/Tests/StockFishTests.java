package Tests;

import Engine.StockFish;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
public class StockFishTests {




    @Test
    void testStockFishBestMove(){
        assertEquals("f1f7", StockFish.getBestMove(13, "1rb1nr2/5pk1/1p1p3p/pPpPb2B/N1P1Q1pq/P7/6PP/1R2NR1K w - - 2 27"));
    }


    @Test
    void testStockFishBestMove2(){
        assertEquals("g2f1", StockFish.getBestMove(13, "N2k2n1/pp4Qp/5n2/5p2/1P6/2PKp1P1/P5qP/RN6 b - - 5 22"));
    }

    @Test
    void testBM3(){
        assertEquals("e6f7", StockFish.getBestMove(13, "8/7k/4K2p/6p1/6P1/7P/8/8 w - - 0 1"));
    }



    @Test
    void testTopLine1(){
        assertEquals("e6f7 h7h8 f7g6 h8g8 g6h6 g8h8 h6g5 h8h7 g5f6 h7g8 g4g5", StockFish.getTopEngineLine(13, "8/7k/4K2p/6p1/6P1/7P/8/8 w - - 0 1"));
    }



























}
