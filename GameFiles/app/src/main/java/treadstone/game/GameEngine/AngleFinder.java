package treadstone.game.GameEngine;

public interface AngleFinder
{
    double radianFinder(double x, double y, double z);
    double calcAngle(double f);
    double adjustAngle(double f);
}
