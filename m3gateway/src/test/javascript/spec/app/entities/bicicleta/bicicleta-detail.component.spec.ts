/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { M3GatewayTestModule } from '../../../test.module';
import { BicicletaDetailComponent } from '../../../../../../main/webapp/app/entities/bicicleta/bicicleta-detail.component';
import { BicicletaService } from '../../../../../../main/webapp/app/entities/bicicleta/bicicleta.service';
import { Bicicleta } from '../../../../../../main/webapp/app/entities/bicicleta/bicicleta.model';

describe('Component Tests', () => {

    describe('Bicicleta Management Detail Component', () => {
        let comp: BicicletaDetailComponent;
        let fixture: ComponentFixture<BicicletaDetailComponent>;
        let service: BicicletaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [M3GatewayTestModule],
                declarations: [BicicletaDetailComponent],
                providers: [
                    BicicletaService
                ]
            })
            .overrideTemplate(BicicletaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BicicletaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BicicletaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Bicicleta(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.bicicleta).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
