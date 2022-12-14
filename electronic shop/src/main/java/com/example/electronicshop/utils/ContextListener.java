package com.example.electronicshop.utils;

import com.example.electronicshop.cart.CartService;
import com.example.electronicshop.cart.CartServiceImpl;
import com.example.electronicshop.dao.CartRepository;
import com.example.electronicshop.dao.CartRepositoryImpl;
import com.example.electronicshop.dao.ConverterResultSet;
import com.example.electronicshop.dao.MySqlUserDao;
import com.example.electronicshop.dao.OrderRepository;
import com.example.electronicshop.dao.OrderRepositoryImpl;
import com.example.electronicshop.dao.ProductRepositoryImpl;
import com.example.electronicshop.dao.TransactionManager;
import com.example.electronicshop.dao.UserDao;
import com.example.electronicshop.order.OrderService;
import com.example.electronicshop.order.OrderServiceImpl;
import com.example.electronicshop.order.processing.ManagerServiceImpl;
import com.example.electronicshop.products.ReadProductRequest;
import com.example.electronicshop.products.ReadProductRequestImpl;
import com.example.electronicshop.security.PermissionReader;
import com.example.electronicshop.security.SecurityConfig;
import com.example.electronicshop.service.ImageService;
import com.example.electronicshop.service.LoginService;
import com.example.electronicshop.service.ProductService;
import com.example.electronicshop.service.RegistrationService;
import com.example.electronicshop.service.UploadService;
import com.example.electronicshop.service.UserBlockerService;
import com.example.electronicshop.service.impl.ImageServiceImpl;
import com.example.electronicshop.service.impl.LoginUserService;
import com.example.electronicshop.service.impl.ProductServiceImpl;
import com.example.electronicshop.service.impl.RegistrationServiceImpl;
import com.example.electronicshop.service.impl.UploadAvatar;
import com.example.electronicshop.service.impl.UserBlockerServiceImpl;
import com.example.electronicshop.servletFilters.localization.CookiesLocalization;
import com.example.electronicshop.servletFilters.localization.SessionLocalization;
import com.example.electronicshop.servletFilters.localization.LocalizationStrategy;
import com.example.electronicshop.validate.ValidateLoginForm;
import com.example.electronicshop.validate.ValidateLoginFormImpl;
import com.example.electronicshop.validate.ValidateOrder;
import com.example.electronicshop.validate.ValidateOrderImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;

import static com.example.electronicshop.servletFilters.localization.LocalizationFilter.LOCALE_STORAGE;
import static com.example.electronicshop.servletFilters.localization.LocalizationFilter.SESSION_LOCALE_STORAGE;
import static com.example.electronicshop.servlets.DrawImageServlets.IMAGE_SERVICE;
import static com.example.electronicshop.servlets.ProductServlets.PRODUCT_SERVICE;
import static com.example.electronicshop.servlets.RegistrationServlets.REGISTRATION_SERVICE;
import static com.example.electronicshop.servlets.UploadFileServlet.UPLOAD_SERVICE;
import static com.example.electronicshop.servlets.UserAuthorizationServlets.LOGIN_SERVICE;

public class ContextListener implements ServletContextListener {
    private ServletContext context;
    public static final String CART_SERVICE = "cartService";
    public static final String ORDER_SERVICE = "orderService";
    public static final String STRATEGY_LOCALIZATION = "localization";
    public static final String MANAGER_SERVICE = "managerService";
    public static final String UNBLOCK_SERVICE = "unblockService";
    private String localeStorage;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        context = sce.getServletContext();
        String localesFileName = context.getInitParameter("locales");
        String securityFileName = context.getInitParameter("permission");
        String localesFileRealPath = context.getRealPath(localesFileName);
        Properties locales = new Properties();
        try {
            locales.load(new FileInputStream(Paths.get(localesFileRealPath).toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        localeStorage = context.getInitParameter(LOCALE_STORAGE);
        context.setAttribute("locales", locales);
        initPool();
        PermissionReader readPermissions = new PermissionReader();
        SecurityConfig.setMapConfig(readPermissions.readFile(securityFileName));
        try {
            initService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void initPool() {
        ConnectionPool connectionPool = new ConnectionPool();
    }

    void initService() throws SQLException {
        ConverterResultSet converterResultSet = new ConverterResultSet();
        TransactionManager transactionManager = new TransactionManager();
        UserDao userDao = new MySqlUserDao(converterResultSet);
        ValidateLoginForm validateLoginForm = new ValidateLoginFormImpl();
        LoginService loginService = new LoginUserService(userDao, transactionManager, validateLoginForm);
        context.setAttribute(LOGIN_SERVICE, loginService);
        ProductRepositoryImpl productRepository = new ProductRepositoryImpl();
        ReadProductRequest readProductRequest = new ReadProductRequestImpl();
        ProductService productService = new ProductServiceImpl(transactionManager
                , productRepository, readProductRequest);
        context.setAttribute(PRODUCT_SERVICE, productService);
        UploadService uploadService = new UploadAvatar();
        context.setAttribute(UPLOAD_SERVICE, uploadService);
        RegistrationService registrationService = new RegistrationServiceImpl();
        context.setAttribute(REGISTRATION_SERVICE, registrationService);
        ImageService imageService = new ImageServiceImpl();
        context.setAttribute(IMAGE_SERVICE, imageService);
        CartRepository cartRepository = new CartRepositoryImpl();
        CartService cart = new CartServiceImpl(cartRepository, transactionManager);
        context.setAttribute(CART_SERVICE, cart);
        LocalizationStrategy strategy;
        if (localeStorage.equalsIgnoreCase(SESSION_LOCALE_STORAGE)) {
            strategy = new SessionLocalization();
        } else {
            strategy = new CookiesLocalization();
        }
        context.setAttribute(STRATEGY_LOCALIZATION, strategy);
        OrderRepository orderRepository = new OrderRepositoryImpl();
        ValidateOrder validateOrder = new ValidateOrderImpl();
        OrderService orderService = new OrderServiceImpl(orderRepository,transactionManager,validateOrder);
        context.setAttribute(ORDER_SERVICE,orderService);
        ManagerServiceImpl managerService = new ManagerServiceImpl(orderRepository,transactionManager);
        context.setAttribute(MANAGER_SERVICE,managerService);
        UserBlockerService userBlockerService = new UserBlockerServiceImpl(userDao,transactionManager);
        context.setAttribute(UNBLOCK_SERVICE,userBlockerService);
    }
}
