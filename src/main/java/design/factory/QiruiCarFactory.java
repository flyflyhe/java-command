package design.factory;

/**
 * 具体品牌汽车生产类
 */
public class QiruiCarFactory extends CarFactoryAbst {
    public CarAbst create() {
        return new QiruiCar();
    }
}