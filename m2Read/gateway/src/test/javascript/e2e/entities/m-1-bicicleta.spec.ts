import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('M1bicicleta e2e test', () => {

    let navBarPage: NavBarPage;
    let m1bicicletaDialogPage: M1bicicletaDialogPage;
    let m1bicicletaComponentsPage: M1bicicletaComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load M1bicicletas', () => {
        navBarPage.goToEntity('m-1-bicicleta');
        m1bicicletaComponentsPage = new M1bicicletaComponentsPage();
        expect(m1bicicletaComponentsPage.getTitle())
            .toMatch(/M 1 Bicicletas/);

    });

    it('should load create M1bicicleta dialog', () => {
        m1bicicletaComponentsPage.clickOnCreateButton();
        m1bicicletaDialogPage = new M1bicicletaDialogPage();
        expect(m1bicicletaDialogPage.getModalTitle())
            .toMatch(/Create or edit a M 1 Bicicleta/);
        m1bicicletaDialogPage.close();
    });

    it('should create and save M1bicicletas', () => {
        m1bicicletaComponentsPage.clickOnCreateButton();
        m1bicicletaDialogPage.setSerieInput('serie');
        expect(m1bicicletaDialogPage.getSerieInput()).toMatch('serie');
        m1bicicletaDialogPage.setModelInput('model');
        expect(m1bicicletaDialogPage.getModelInput()).toMatch('model');
        m1bicicletaDialogPage.setTipInput('tip');
        expect(m1bicicletaDialogPage.getTipInput()).toMatch('tip');
        m1bicicletaDialogPage.setPretInput('5');
        expect(m1bicicletaDialogPage.getPretInput()).toMatch('5');
        m1bicicletaDialogPage.setDescriereInput('descriere');
        expect(m1bicicletaDialogPage.getDescriereInput()).toMatch('descriere');
        m1bicicletaDialogPage.setAnFabricatieInput('5');
        expect(m1bicicletaDialogPage.getAnFabricatieInput()).toMatch('5');
        m1bicicletaDialogPage.setStatusInput('5');
        expect(m1bicicletaDialogPage.getStatusInput()).toMatch('5');
        m1bicicletaDialogPage.save();
        expect(m1bicicletaDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class M1bicicletaComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-m-1-bicicleta div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class M1bicicletaDialogPage {
    modalTitle = element(by.css('h4#myM1bicicletaLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    serieInput = element(by.css('input#field_serie'));
    modelInput = element(by.css('input#field_model'));
    tipInput = element(by.css('input#field_tip'));
    pretInput = element(by.css('input#field_pret'));
    descriereInput = element(by.css('input#field_descriere'));
    anFabricatieInput = element(by.css('input#field_anFabricatie'));
    statusInput = element(by.css('input#field_status'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setSerieInput = function(serie) {
        this.serieInput.sendKeys(serie);
    };

    getSerieInput = function() {
        return this.serieInput.getAttribute('value');
    };

    setModelInput = function(model) {
        this.modelInput.sendKeys(model);
    };

    getModelInput = function() {
        return this.modelInput.getAttribute('value');
    };

    setTipInput = function(tip) {
        this.tipInput.sendKeys(tip);
    };

    getTipInput = function() {
        return this.tipInput.getAttribute('value');
    };

    setPretInput = function(pret) {
        this.pretInput.sendKeys(pret);
    };

    getPretInput = function() {
        return this.pretInput.getAttribute('value');
    };

    setDescriereInput = function(descriere) {
        this.descriereInput.sendKeys(descriere);
    };

    getDescriereInput = function() {
        return this.descriereInput.getAttribute('value');
    };

    setAnFabricatieInput = function(anFabricatie) {
        this.anFabricatieInput.sendKeys(anFabricatie);
    };

    getAnFabricatieInput = function() {
        return this.anFabricatieInput.getAttribute('value');
    };

    setStatusInput = function(status) {
        this.statusInput.sendKeys(status);
    };

    getStatusInput = function() {
        return this.statusInput.getAttribute('value');
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
