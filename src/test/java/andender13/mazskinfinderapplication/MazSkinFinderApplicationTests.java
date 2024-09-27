package andender13.mazskinfinderapplication;

import andender13.mazskinfinderapplication.entity.User;
import andender13.mazskinfinderapplication.entity.Weapon;
import andender13.mazskinfinderapplication.service.UserService;
import andender13.mazskinfinderapplication.service.WeaponService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MazSkinFinderApplicationTests {
    @Autowired
    WeaponService weaponService;
    @Autowired
    UserService userService;
    @Test
    public void TestLazy()
    {
        List<Weapon> list = weaponService.getAllWeapons();
        User user = list.getFirst().getUser();
    }
    @Test
    public void testSelectForSearch(){
        List<User> list= userService.findAllSearchReady();
        System.out.println(list.getFirst().getWeapon());
    }


}
