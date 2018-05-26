import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Ordin e2e test', () => {

    let navBarPage: NavBarPage;
    let ordinDialogPage: OrdinDialogPage;
    let ordinComponentsPage: OrdinComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Ordins', () => {
        navBarPage.goToEntity('ordin');
        ordinComponentsPage = new OrdinComponentsPage();
        expect(ordinComponentsPage.getTitle())
            .toMatch(/Ordins/);

    });

    it('should load create Ordin dialog', () => {
        ordinComponentsPage.clickOnCreateButton();
        ordinDialogPage = new OrdinDialogPage();
        expect(ordinDialogPage.getModalTitle())
            .toMatch(/Create or edit a Ordin/);
        ordinDialogPage.close();
    });

    it('should create and save Ordins', () => {
        ordinComponentsPage.clickOnCreateButton();
        ordinDialogPage.setSerie_userInput('serie_user');
        expect(ordinDialogPage.getSerie_userInput()).toMatch('serie_user');
        ordinDialogPage.setData_inchiriereInput('2000-12-31');
        expect(ordinDialogPage.getData_inchiriereInput()).toMatch('2000-12-31');
        ordinDialogPage.setId_bikeInput('5');
        expect(ordinDialogPage.getId_bikeInput()).toMatch('5');
        ordinDialogPage.save();
        expect(ordinDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class OrdinComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-ordin div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class OrdinDialogPage {
    modalTitle = element(by.css('h4#myOrdinLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    serie_userInput = element(by.css('input#field_serie_user'));
    data_inchiriereInput = element(by.css('input#field_data_inchiriere'));
    id_bikeInput = element(by.css('input#field_id_bike'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setSerie_userInput = function(serie_user) {
        this.serie_userInput.sendKeys(serie_user);
    };

    getSerie_userInput = function() {
        return this.serie_userInput.getAttribute('value');
    };

    setData_inchiriereInput = function(data_inchiriere) {
        this.data_inchiriereInput.sendKeys(data_inchiriere);
    };

    getData_inchiriereInput = function() {
        return this.data_inchiriereInput.getAttribute('value');
    };

    setId_bikeInput = function(id_bike) {
        this.id_bikeInput.sendKeys(id_bike);
    };

    getId_bikeInput = function() {
        return this.id_bikeInput.getAttribute('value');
    };

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
