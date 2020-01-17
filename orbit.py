from pygame import *
import math

class Planet:
    def __init__(self,screen,x,y,width,mass,color):
        self.screen = screen
        self.rect = Rect(0,0,width,width)
        self.rect.center = x,y
        self.mass = mass
        self.orbiting = None
        self.color = color
        

    def orbit(self,planet):
        self.orbiting = planet
        self.getVelocity()
        self.getDirection()

    def getVelocity(self):
        if (self.orbiting):
            d = math.sqrt((self.orbiting.rect.centery - self.rect.centery)**2 +
                          (self.orbiting.rect.centerx - self.rect.centerx)**2)
            self.velocity = self.orbiting.mass/d


    def getDirection(self):
        if self.orbiting != None:
            dy = self.rect.centery - self.orbiting.rect.centery  
            dx = self.orbiting.rect.centerx - self.rect.centerx
            try:
                angle = math.atan(dy/dx)
            except:
                if (dy > 0):
                    angle = 0
                else:
                    angle = math.pi
            if (dy < 0 and dx < 0):
                angle += math.pi 
            elif dx < 0:
                angle = math.pi + angle
            elif dy < 0:
                angle = 2*math.pi + angle
            
            self.angle = angle - math.pi/2
        
            
        

    def update(self):
        dX = math.ceil(self.velocity*math.cos(self.angle))
        dY = math.ceil(self.velocity*math.sin(self.angle))
        
        self.rect.centerx += dX
        self.rect.centery -= dY
        self.getDirection()

    def draw(self):
        draw.circle(self.screen,self.color,self.rect.center,self.rect.width)


def main():
    screen = display.set_mode((800,800))
    sun = Planet(screen,400,400,50,1000,(255,255,0))
    earth = Planet(screen,550,400,10,100,(0,0,255))
    earth.orbit(sun)
    moon = Planet(screen,550,450,2,1,(100,100,100))
    moon.orbit(earth)
    mars = Planet(screen,700,400,5,5,(255,0,0))
    mars.orbit(sun)

    clock  =time.Clock()
    while True:
        for e in event.get():
            if e.type == KEYDOWN:
                if e.key == K_q:
                    quit()
        earth.update()
        mars.update()
        moon.update()
        
        screen.fill((0,0,0))
        moon.draw()
        sun.draw()
        earth.draw()
        mars.draw()
        display.flip()
        clock.tick(60)

main()
