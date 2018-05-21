import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Bicicleta e2e test', () => {

    let navBarPage: NavBarPage;
    let bicicletaDialogPage: BicicletaDialogPage;
    let bicicletaComponentsPage: BicicletaComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Bicicletas', () => {
        navBarPage.goToEntity('bicicleta');
        bicicletaComponentsPage = new BicicletaComponentsPage();
        expect(bicicletaComponentsPage.getTitle())
            .toMatch(/Bicicletas/);

    });

    it('should load create Bicicleta dialog', () => {
        bicicletaComponentsPage.clickOnCreateButton();
        bicicletaDialogPage = new BicicletaDialogPage();
        expect(bicicletaDialogPage.getModalTitle())
            .toMatch(/Create or edit a Bicicleta/);
        bicicletaDialogPage.close();
    });

    it('should create and save Bicicletas', () => {
        bicicletaComponentsPage.clickOnCreateButton();
        bicicletaDialogPage.setSerieInput('serie');
        expect(bicicletaDialogPage.getSerieInput()).toMatch('serie');
        bicicletaDialogPage.setPretInput('5');
        expect(bicicletaDialogPage.getPretInput()).toMatch('5');
        bicicletaDialogPage.setData_inchiriereInput('2000-12-31');
        expect(bicicletaDialogPage.getData_inchiriereInput()).toMatch('2000-12-31');
        bicicletaDialogPage.setTimp_inchiriereInput('5');
        expect(bicicletaDialogPage.getTimp_inchiriereInput()).toMatch('5');
        bicicletaDialogPage.setData_returnInput('2000-12-31');
        expect(bicicletaDialogPage.getData_returnInput()).toMatch('2000-12-31');
        bicicletaDialogPage.setStatusInput('5');
        expect(bicicletaDialogPage.getStatusInput()).toMatch('5');
        bicicletaDialogPage.setCnp_renterInput('cnp_renter');
        expect(bicicletaDialogPage.getCnp_renterInput()).toMatch('cnp_renter');
        bicicletaDialogPage.save();
        expect(bicicletaDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BicicletaComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-bicicleta div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class BicicletaDialogPage {
    modalTitle = element(by.css('h4#myBicicletaLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    serieInput = element(by.css('input#field_serie'));
    pretInput = element(by.css('input#field_pret'));
    data_inchiriereInput = element(by.css('input#field_data_inchiriere'));
    timp_inchiriereInput = element(by.css('input#field_timp_inchiriere'));
    data_returnInput = element(by.css('input#field_data_return'));
    statusInput = element(by.css('input#field_status'));
    cnp_renterInput = element(by.css('input#field_cnp_renter'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setSerieInput = function(serie) {
        this.serieInput.sendKeys(serie);
    };

    getSerieInput = function() {
        return this.serieInput.getAttribute('value');
    };

    setPretInput = function(pret) {
        this.pretInput.sendKeys(pret);
    };

    getPretInput = function() {
        return this.pretInput.getAttribute('value');
    };

    setData_inchiriereInput = function(data_inchiriere) {
        this.data_inchiriereInput.sendKeys(data_inchiriere);
    };

    getData_inchiriereInput = function() {
        return this.data_inchiriereInput.getAttribute('value');
    };

    setTimp_inchiriereInput = function(timp_inchiriere) {
        this.timp_inchiriereInput.sendKeys(timp_inchiriere);
    };

    getTimp_inchiriereInput = function() {
        return this.timp_inchiriereInput.getAttribute('value');
    };

    setData_returnInput = function(data_return) {
        this.data_returnInput.sendKeys(data_return);
    };

    getData_returnInput = function() {
        return this.data_returnInput.getAttribute('value');
    };

    setStatusInput = function(status) {
        this.statusInput.sendKeys(status);
    };

    getStatusInput = function() {
        return this.statusInput.getAttribute('value');
    };

    setCnp_renterInput = function(cnp_renter) {
        this.cnp_renterInput.sendKeys(cnp_renter);
    };

    getCnp_renterInput = function() {
        return this.cnp_renterInput.getAttribute('value');
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
