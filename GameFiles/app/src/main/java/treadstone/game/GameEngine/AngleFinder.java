package treadstone.game.GameEngine;

public interface AngleFinder
{
    double radianFinder(float x, float y, float z);
    double calcAngle(double f);
    double adjustAngle(double f);
}
