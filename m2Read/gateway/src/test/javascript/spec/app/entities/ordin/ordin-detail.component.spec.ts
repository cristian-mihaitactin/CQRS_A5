/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GatewayTestModule } from '../../../test.module';
import { OrdinDetailComponent } from '../../../../../../main/webapp/app/entities/ordin/ordin-detail.component';
import { OrdinService } from '../../../../../../main/webapp/app/entities/ordin/ordin.service';
import { Ordin } from '../../../../../../main/webapp/app/entities/ordin/ordin.model';

describe('Component Tests', () => {

    describe('Ordin Management Detail Component', () => {
        let comp: OrdinDetailComponent;
        let fixture: ComponentFixture<OrdinDetailComponent>;
        let service: OrdinService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [OrdinDetailComponent],
                providers: [
                    OrdinService
                ]
            })
            .overrideTemplate(OrdinDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdinDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdinService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Ordin(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.ordin).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
