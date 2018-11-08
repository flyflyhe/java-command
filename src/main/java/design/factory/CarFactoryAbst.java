package design.factory;

/**
 * 汽车生产工厂抽象类  具体生产汽车到 具体子类中 易于扩展 符合开闭原则
 */
public abstract class CarFactoryAbst {
    public abstract CarAbst create();
}