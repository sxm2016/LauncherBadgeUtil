package com.shixm.commonlib.launchbadger;

import android.text.TextUtils;

import com.shixm.commonlib.launchbadger.impl.AdwHomeBadger;
import com.shixm.commonlib.launchbadger.impl.ApexHomeBadger;
import com.shixm.commonlib.launchbadger.impl.AsusHomeLauncher;
import com.shixm.commonlib.launchbadger.impl.DefaultBadger;
import com.shixm.commonlib.launchbadger.impl.LGHomeBadger;
import com.shixm.commonlib.launchbadger.impl.NewHtcHomeBadger;
import com.shixm.commonlib.launchbadger.impl.NovaHomeBadger;
import com.shixm.commonlib.launchbadger.impl.SamsungHomeBadger;
import com.shixm.commonlib.launchbadger.impl.SolidHomeBadger;
import com.shixm.commonlib.launchbadger.impl.SonyHomeBadger;
import com.shixm.commonlib.launchbadger.impl.XiaomiHomeBadger;

import java.util.List;

public enum BadgerType {
    DEFAULT {
        @Override
        public Badger initBadger() {
            return new DefaultBadger();
        }
    }, ADW {
        @Override
        public Badger initBadger() {
            return new AdwHomeBadger();
        }
    }, APEX {
        @Override
        public Badger initBadger() {
            return new ApexHomeBadger();
        }
    }, ASUS {
        @Override
        public Badger initBadger() {
            return new AsusHomeLauncher();
        }
    }, LG {
        @Override
        public Badger initBadger() {
            return new LGHomeBadger();
        }
    }, HTC {
        @Override
        public Badger initBadger() {
            return new NewHtcHomeBadger();
        }
    }, NOVA {
        @Override
        public Badger initBadger() {
            return new NovaHomeBadger();
        }
    }, SAMSUNG {
        @Override
        public Badger initBadger() {
            return new SamsungHomeBadger();
        }
    }, SOLID {
        @Override
        public Badger initBadger() {
            return new SolidHomeBadger();
        }
    }, SONY {
        @Override
        public Badger initBadger() {
            return new SonyHomeBadger();
        }
    }, XIAO_MI {
        @Override
        public Badger initBadger() {
            return new XiaomiHomeBadger();
        }
    };

    public Badger badger;

    public static Badger getBadgerByLauncherName(String launcherName) {
        Badger result = new DefaultBadger();
        if (!TextUtils.isEmpty(launcherName))
            for (BadgerType badgerType : BadgerType.values()) {
                if (badgerType.getSupportLaunchers().contains(launcherName)) {
                    result = badgerType.getBadger();
                    break;
                }
            }
        return result;
    }

    public Badger getBadger() {
        if (badger == null)
            badger = initBadger();
        return badger;
    }

    public abstract Badger initBadger();

    public List<String> getSupportLaunchers() {
        return getBadger().getSupportLaunchers();
    }
}
