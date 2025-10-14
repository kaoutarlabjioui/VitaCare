//package org.vitacare.config;
//
//import jakarta.persistence.EntityManager;
//import org.vitacare.repository.UserRepository;
//import org.vitacare.repository.implementation.UserRepositoryImp;
//import org.vitacare.service.AuthService;
//import org.vitacare.service.impl.AuthServiceImp;
//import org.vitacare.utils.JpaUtil;
//
//public class AppContainer {
//
//    private static volatile AppContainer instance; // thread-safe
//
//    private final EntityManager em;
////    private final UserRepository userRepository;
////    private final AuthService authService;
//
//    private AppContainer() {
//        this.em = JpaUtil.getEntityManager();
////        this.userRepository = new UserRepositoryImp(em);
////        this.authService = new AuthServiceImp(userRepository);
//    }
//
//    public static AppContainer getInstance() {
//        if (instance == null) {
//            synchronized (AppContainer.class) {
//                if (instance == null) {
//                    instance = new AppContainer();
//                }
//            }
//        }
//        return instance;
//    }
//
////    public AuthService getAuthService() {
////        return authService;
////    }
//
//    public void close() {
//        if (em.isOpen()) {
//            em.close();
//        }
//    }
//}
