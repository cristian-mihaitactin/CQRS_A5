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
        bicicletaDialogPage.setTipInput('tip');
        expect(bicicletaDialogPage.getTipInput()).toMatch('tip');
        bicicletaDialogPage.setModelInput('model');
        expect(bicicletaDialogPage.getModelInput()).toMatch('model');
        bicicletaDialogPage.setAnFabricatieInput('5');
        expect(bicicletaDialogPage.getAnFabricatieInput()).toMatch('5');
        bicicletaDialogPage.setStatusInput('5');
        expect(bicicletaDialogPage.getStatusInput()).toMatch('5');
        bicicletaDialogPage.setDescriereInput('descriere');
        expect(bicicletaDialogPage.getDescriereInput()).toMatch('descriere');
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
    tipInput = element(by.css('input#field_tip'));
    modelInput = element(by.css('input#field_model'));
    anFabricatieInput = element(by.css('input#field_anFabricatie'));
    statusInput = element(by.css('input#field_status'));
    descriereInput = element(by.css('input#field_descriere'));

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

    setTipInput = function(tip) {
        this.tipInput.sendKeys(tip);
    };

    getTipInput = function() {
        return this.tipInput.getAttribute('value');
    };

    setModelInput = function(model) {
        this.modelInput.sendKeys(model);
    };

    getModelInput = function() {
        return this.modelInput.getAttribute('value');
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

    setDescriereInput = function(descriere) {
        this.descriereInput.sendKeys(descriere);
    };

    getDescriereInput = function() {
        return this.descriereInput.getAttribute('value');
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
