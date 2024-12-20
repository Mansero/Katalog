package com.example.Catalog;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class BookPlayWrightTest {
        public static void main(String[] args) {

                //Prepare Playwright
            try (Playwright playwright = Playwright.create()) {
                Browser browser = playwright.chromium().launch();
                Page page = browser.newPage();

                //Create Request
                page.navigate("http://localhost:8081");
                page.fill("input[name='searchTerm']", "Spring Boot in Action");
                page.click("button[type='submit']");

                //Check Response
                //Book visible?
                Locator bookTitle = page.locator("strong a:has-text('Spring Boot in Action')");
                if (bookTitle.isVisible()) {
                    System.out.println("Das erwartete Buch wird angezeigt.");

                    //Add to Shopping-Cart
                    page.click("a[href*='/shopping-cart/add/']");

                    //Book added to Shopping-Cart & visible?
                    page.navigate("http://localhost:8081/shopping-cart");
                    Locator cartItem = page.locator("tr", new Page.LocatorOptions().setHasText("Spring Boot in Action"));
                    if (cartItem.isVisible()) {
                        System.out.println("Der Warenkorb enthält das ausgewählte Buch.");
                    } else {
                        System.out.println("Der Warenkorb enthält das ausgewählte Buch NICHT.");
                    }
                } else {
                    //Book not visible
                    System.out.println("Das erwartete Buch wird NICHT angezeigt.");
                }
                // Browser schließen
                browser.close();
            }
        }
}
